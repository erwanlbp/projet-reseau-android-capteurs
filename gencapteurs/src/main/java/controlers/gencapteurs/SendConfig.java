package controlers.gencapteurs;

import java.net.*;
import java.util.Enumeration;

public class SendConfig {

    private static final String EHT0 = "wlp";

    public void send(int portEnvoi, int portEcoute, String ipDest) {

        String ipGenCapteur = getIpAdress();

        if (ipGenCapteur != null) {
            DatagramSocket client;

            String data = portEcoute + ":" + ipGenCapteur + ":";
            byte[] buffer = data.getBytes();

            try {
                client = new DatagramSocket();

                InetAddress adresse = InetAddress.getByName(ipDest);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, portEnvoi);

                packet.setData(buffer);
                client.send(packet);

                System.out.println("Send config : " + ipGenCapteur + " - " + portEnvoi);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getIpAdress() {
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
                    if (address.getHostAddress().contains(EHT0))
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
