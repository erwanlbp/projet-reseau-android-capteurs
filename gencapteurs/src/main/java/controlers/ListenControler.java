package controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.LightCapteur;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenControler {

    private static final int SLEEP_TIME = 800;

    private int port;
    private ObjectMapper objectMapper;
    private firebase.FirebaseClient firebaseClient;

    public ListenControler(int port) {
        this.port = port;
    }

    public void start() {
        objectMapper = new ObjectMapper();
        firebaseClient = new firebase.FirebaseClient();
        firebaseClient.initFirebaseClient();
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
                    firebaseClient.sentPutRequet(lightCapteur);
                }
                if (s.contains("TEMPERATURE")) {
                    temperatureCapteur = objectMapper.readValue(s, TemperatureCapteur.class);
                    System.out.println("##### Received flux : " + packet.getAddress() + " - " + temperatureCapteur);
                    firebaseClient.sentPutRequet(temperatureCapteur);
                }

                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}