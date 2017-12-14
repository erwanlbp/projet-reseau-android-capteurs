package controlers.gencapteurs;

import java.net.*;
import java.util.Enumeration;

public class SendConfig {

    public void send(int portDest, int portListen, String ipDest, String netInterfaceName) {

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

    //TODO to improve
    private String getIpAdress(String netInterfaceName) {
        boolean isETH0 = false;

        try {
            Enumeration<NetworkInterface> list = NetworkInterface.getNetworkInterfaces();

            while (list.hasMoreElements()) {

                NetworkInterface ni = list.nextElement();
                Enumeration<InetAddress> listAddress = ni.getInetAddresses();

                while (listAddress.hasMoreElements()) {
                    InetAddress address = listAddress.nextElement();
                    if (isETH0)
                        return address.getHostAddress();
                    if (address.getHostAddress().contains(netInterfaceName))
                        isETH0 = true;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("No ip adress found");
        return null;
    }
}
