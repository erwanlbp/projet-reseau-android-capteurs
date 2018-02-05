package controlers.gencapteurs;

import java.net.*;
import java.util.Enumeration;

/**
 * L'envoi de la config sur le reseau.
 */
public class SendConfig {
    /**
     * @param portDest         le port de destination
     * @param portListen       Le port d'écoute
     * @param ipDest           L'ip de destination
     * @param netInterfaceName Le nom de l'interface
     */
    public void send(final int portDest, final int portListen, final String ipDest, final String netInterfaceName) {

        String ipGenCapteur = getIpAdress(netInterfaceName);

        if (ipGenCapteur != null) {
            DatagramSocket client;

            String data = portListen + ":" + ipGenCapteur + ":";
            byte[] buffer = data.getBytes();

            try {
                client = new DatagramSocket();

                InetAddress adresse = InetAddress.getByName(ipDest);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, portDest);

                packet.setData(buffer);
                client.send(packet);

                System.out.println("Send config : " + ipGenCapteur + " - " + portDest);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param netInterfaceName Le nom de l'interface
     * @return L'ip vers laquelle envoyer
     */
    //TODO to improve
    private String getIpAdress(final String netInterfaceName) {
        boolean isETH0 = false;

        try {
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();

            while (list.hasMoreElements()) {

                NetworkInterface ni = list.nextElement();
                Enumeration<InetAddress> listAddress = ni.getInetAddresses();

                while (listAddress.hasMoreElements()) {
                    InetAddress address = listAddress.nextElement();
                    if (isETH0) {
                        return address.getHostAddress();
                    }
                    if (address.getHostAddress().contains(netInterfaceName)) {
                        isETH0 = true;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("No ip adress found");
        return null;
    }
}
