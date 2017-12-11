package controlers.dbinterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import firebase.FirebaseClient;
import model.Capteur;
import model.LightCapteur;
import model.NetworkConfig;
import model.TemperatureCapteur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DBInterfaceController {

    private int port;
    private ObjectMapper objectMapper;
    private List<String> listCapteur;

    public DBInterfaceController(int port) throws IOException, InterruptedException {
        this.port = port;
        objectMapper = new ObjectMapper();
        listCapteur = new ArrayList<>();
    }

    public void init() throws IOException, InterruptedException {
        FirebaseClient.getInstance().listenStartStop((capteur) -> sendStartStop(capteur));
        receiveFlux();
    }

    private void sendStartStop(Capteur capteur) {
        int serverPort = NetworkConfig.getInstance().getPortDestGenCapteurs();
        String msg = capteur.getName() + " : " + capteur.isActivated();

        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress server = InetAddress.getByName(NetworkConfig.getInstance().getIpDestGenCapteurs());
            int msgLength = msg.length();
            byte[] msgByte = msg.getBytes();
            DatagramPacket dp = new DatagramPacket(msgByte, msgLength, server, serverPort);
            ds.send(dp);
            System.out.println("start/stop : " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveFlux() {
        DatagramSocket client;

        try {
            byte[] buffer = new byte[8196];

            //On met le serveur en écoute
            client = new DatagramSocket(port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                analyseAndSendPacket(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void analyseAndSendPacket(DatagramPacket packet) throws IOException, InterruptedException {
        Capteur capteur = null;
        boolean found = false;

        String s = new String(packet.getData());

        if (NetworkConfig.getInstance().isSet()) {
            if (s.contains("LIGHT")) {
                capteur = objectMapper.readValue(s, LightCapteur.class);
                found = true;
            }
            if (s.contains("TEMPERATURE")) {
                capteur = objectMapper.readValue(s, TemperatureCapteur.class);
                found = true;
            }
        }
        if (found) {
            System.out.println("Received flux : " + packet.getAddress() + " - " + capteur);
            if (!listCapteur.contains(capteur.getName())) {
                FirebaseClient.getInstance().sendCapteur(capteur);
                listCapteur.add(capteur.getName());
            }
            FirebaseClient.getInstance().sendData(capteur);
        } else {
            String[] bufferSplitted = s.split(":");
            NetworkConfig.getInstance().setPortDestGenCapteurs(Integer.valueOf(bufferSplitted[0]));
            NetworkConfig.getInstance().setIpDestGenCapteurs(bufferSplitted[1]);
            System.out.println("Config received !!!");
        }
    }
}
