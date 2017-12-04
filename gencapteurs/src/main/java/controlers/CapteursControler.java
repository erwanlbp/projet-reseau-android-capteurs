package controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import firebase.FirebaseClient;
import model.LightCapteur;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CapteursControler {

    private static final int SLEEP_TIME = 800;

    private int port;
    private ObjectMapper objectMapper;
    private FirebaseClient firebaseClient;

    public CapteursControler(int port) {
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
        objectMapper = new ObjectMapper();
        firebaseClient = new FirebaseClient();
    }

    public void receivedFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                LightCapteur lightCapteur;
                TemperatureCapteur temperatureCapteur;

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);

                String s = new String(packet.getData());
                if (s.contains("LIGHT")) {
                    lightCapteur = objectMapper.readValue(s, LightCapteur.class);
                    System.out.println("##### Received flux : " + packet.getAddress() + " - " + lightCapteur);
                    firebaseClient.sentPutRequest(lightCapteur);
                }
                if (s.contains("TEMPERATURE")) {
                    temperatureCapteur = objectMapper.readValue(s, TemperatureCapteur.class);
                    System.out.println("##### Received flux : " + packet.getAddress() + " - " + temperatureCapteur);
                    firebaseClient.sentPutRequest(temperatureCapteur);
                }

                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}