package controlers.db_interface;

import com.fasterxml.jackson.databind.ObjectMapper;
import firebase.FirebaseClient;
import model.Capteur;
import model.LightCapteur;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DBInterfaceController {
    
    private int port;
    private ObjectMapper objectMapper;
    private FirebaseClient firebaseClient;

    public DBInterfaceController(int port) throws IOException, InterruptedException {
        this.port = port;
        this.firebaseClient = new FirebaseClient();
        objectMapper = new ObjectMapper();
    }

    public void receiveFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                analysePacket(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analysePacket(DatagramPacket packet) throws IOException {
        Capteur capteur = null;
        boolean found = false;

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
        } else {
            String[] bufferSplitted = s.split(":");
            firebaseClient.sendPortIn(Integer.valueOf(bufferSplitted[0]));
            firebaseClient.sendIpDest(bufferSplitted[1]);
        }
    }
}
