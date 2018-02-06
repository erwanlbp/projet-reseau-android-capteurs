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

/**
 * Pour lancer le programme en interface DB.
 */
public class DBInterfaceController {

    /**
     * Le port d'écoute.
     */
    private int portListen;
    /**
     * Pour parser et formatter les objets en JSON.
     */
    private ObjectMapper objectMapper;
    /**
     * La liste des capteurs du réseau.
     */
    private List<String> listCapteur;

    /**
     * Constructeur à utiliser.
     *
     * @param pportListen Le port d'écoute
     * @throws IOException          Exception
     * @throws InterruptedException Exception
     */
    public DBInterfaceController(final int pportListen) throws IOException, InterruptedException {
        this.portListen = pportListen;
        objectMapper = new ObjectMapper();
        listCapteur = new ArrayList<>();
    }

    /**
     * Pour lancer l'écoute des start/stop sur Firebase.
     *
     * @throws IOException          Exception
     * @throws InterruptedException Exception
     */
    public void init() throws IOException, InterruptedException {
        FirebaseClient.getInstance().listenStartStop((capteur) -> sendStartStop(capteur));
        receiveFlux();
    }

    /**
     * Pour envoyer un start/stop sur le réseau.
     *
     * @param capteur Le capteur à stopper
     */
    private void sendStartStop(final Capteur capteur) {
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

    /**
     * Ecoute les flux du réseau de capteurs.
     */
    private void receiveFlux() {
        DatagramSocket client;

        try {

            //On met le serveur en écoute
            client = new DatagramSocket(portListen);

            while (true) {
                byte[] buffer = new byte[8196];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                client.receive(packet);
                analyseAndSendPacket(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyse le paquet recu du réseau.
     *
     * @param packet Le paquet UDP
     * @throws IOException          Exception
     * @throws InterruptedException Exception
     */
    private void analyseAndSendPacket(final DatagramPacket packet) throws IOException, InterruptedException {
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
            NetworkConfig.getInstance().setPortDestGenCapteurs(Integer.parseInt(bufferSplitted[0]));
            NetworkConfig.getInstance().setIpDestGenCapteurs(bufferSplitted[1]);
            System.out.println("Config received !!!");
        }
    }
}
