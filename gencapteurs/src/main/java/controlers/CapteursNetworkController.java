package controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import firebase.FirebaseClient;
import model.Capteur;
import model.LightCapteur;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CapteursNetworkController {

    private static final int SLEEP_TIME = 800;

    private int port;
    private ObjectMapper objectMapper;
    private FirebaseClient firebaseClient;

    public CapteursNetworkController(int port) throws IOException, InterruptedException {
        this.port = port;
        objectMapper = new ObjectMapper();
        firebaseClient = new FirebaseClient();
    }

    public void receiveFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                Capteur capteur = null;
                boolean found = false;

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);

                String s = new String(packet.getData());
                if (s.contains("LIGHT")) {
                    capteur = objectMapper.readValue(s, LightCapteur.class);
                    found = true;
                }
                if (s.contains("TEMPERATURE")) {
                    capteur = objectMapper.readValue(s, TemperatureCapteur.class);
                    found = true;
                }
                if (found) {
                    System.out.println("Received flux : " + packet.getAddress() + " - " + capteur);
                    firebaseClient.sendPutRequest(capteur);
                }

                Thread.sleep(SLEEP_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
