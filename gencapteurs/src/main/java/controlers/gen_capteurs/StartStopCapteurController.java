package controlers.gen_capteurs;

import model.Capteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class StartStopCapteurController {

    private int port;
    private List<Capteur> capteurList;

    public StartStopCapteurController(int port, List<Capteur> capteurList) {
        this.port = port;
        this.capteurList = capteurList;
    }

    public void receptionStartStopFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                String s = new String(packet.getData());

                System.out.println("Received start / stop command : " + s);

                startStopCapteur(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startStopCapteur(String s) {
        for (Capteur c : capteurList) {
            if (s.contains(c.getName())) {
                if (s.contains("true")) {
                    c.setActivated(true);
                    System.out.println("Received start command : " + c);
                }
                if (s.contains("false")) {
                    c.setActivated(false);
                    System.out.println("Received stop command : " + c);
                }
            }
        }
    }
}
