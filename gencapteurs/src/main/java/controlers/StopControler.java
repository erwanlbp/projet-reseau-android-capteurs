package controlers;

import java.io.IOException;
import java.net.*;

public class StopControler {

    private String ipDest;
    private int port;

    public StopControler(String ipDest, int port) {
        this.ipDest = ipDest;
        this.port = port;
    }

    public void start() {
        sendFlux();
    }

    private void sendFlux() {
        DatagramSocket client;

        String s = "LightCapteur : false";

        byte[] buffer = s.getBytes();

        try {
            client = new DatagramSocket();

            //On crée notre datagramme
            InetAddress adresse = InetAddress.getByName(ipDest);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);

            //On lui affecte les données à envoyer
            packet.setData(buffer);

            //On envoie au serveur
            client.send(packet);

            System.out.println("##### Send stop command : " + s);

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}