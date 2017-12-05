package controlers.gen_capteurs;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendConfig {

    public SendConfig() {
    }

    public void sendConfig(int portOut, int portIn, String ipDest) {
        String ipGenCapteur = InetAddress.getLoopbackAddress().getHostAddress();
        sendIt(String.valueOf(portOut), String.valueOf(portIn), ipDest, ipGenCapteur);
    }

    private void sendIt(String portEnvoi, String portEcoute, String ipDest, String ipGenCapteur) {
        DatagramSocket client;

        String data = portEcoute + ":" + ipGenCapteur;
        byte[] buffer = data.getBytes();

        try {
            client = new DatagramSocket();

            //On crée notre datagramme
            InetAddress adresse = InetAddress.getByName(ipDest);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, Integer.parseInt(portEnvoi));

            //On lui affecte les données à envoyer
            packet.setData(buffer);

            //On envoie au serveur
            client.send(packet);

            System.out.println("Send config : " + ipGenCapteur + " - " + portEnvoi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
