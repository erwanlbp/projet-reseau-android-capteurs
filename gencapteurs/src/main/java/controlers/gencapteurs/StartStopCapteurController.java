package controlers.gencapteurs;

import model.Capteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

/**
 * L'écoute des start stop message.
 */
public class StartStopCapteurController {
    /**
     * Le port d'écoute des start stop.
     */
    private int portListen;
    /**
     * La liste des capteurs pour les stopper.
     */
    private List<Capteur> capteurList;

    /**
     * @param pportListen  Le port d'écoute
     * @param pcapteurList La liste des capteurs
     */
    public StartStopCapteurController(final int pportListen, final List<Capteur> pcapteurList) {
        this.portListen = pportListen;
        this.capteurList = pcapteurList;
    }

    /**
     * Boucle de réception des messages.
     */
    public void receptionStartStopFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(portListen);

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

    /**
     * Start stop un capteur en fonction du message.
     *
     * @param s Le message
     */
    private void startStopCapteur(final String s) {
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
