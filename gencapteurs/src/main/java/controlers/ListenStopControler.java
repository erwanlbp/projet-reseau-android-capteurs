package controlers;

import model.Capteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class ListenStopControler {

    private static final int ITERATION_LIGHT_CAPTEUR = 3;
    private static final int ITERATION_TEMPERATURE_CAPTEUR = 1;
    private static final int SLEEP_TIME = 1000;

    private int port;
    private List<Capteur> capteurList;

    public ListenStopControler(int port, List<Capteur> capteurList) {
        this.port = port;
        this.capteurList = capteurList;
    }

    public void receivedStopFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);

                String s = new String(packet.getData());

                System.out.println("##### Received start / stop command : " + s);

                stopCapteur(s);

                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void stopCapteur(String s) {
        for (Capteur c : capteurList) {
            if (s.contains(c.getName()) && s.contains("true")) {
                c.setActivated(true);
                System.out.println("##### Received start command : " + c);
            }
            if (s.contains(c.getName()) && s.contains("false")) {
                c.setActivated(false);
                System.out.println("##### Received stop command : " + c);
            }
        }
    }
}