package controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Capteur;
import model.LightCapteur;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceivedControler {

    private static final int SLEEP_TIME = 800;

    private int port;
    private ObjectMapper objectMapper;

    public ReceivedControler(int port) {
        this.port = port;
    }

    public void start() {
        objectMapper = new ObjectMapper();
        receivedFlux();
    }

    private void receivedFlux() {
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
                }
                if (s.contains("TEMPERATURE")) {
                    temperatureCapteur = objectMapper.readValue(s, TemperatureCapteur.class);
                    System.out.println("##### Received flux : " + packet.getAddress() + " - " + temperatureCapteur);
                }

                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}