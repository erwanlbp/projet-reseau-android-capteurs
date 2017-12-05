package controlers.gen_capteurs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Capteur;
import model.LightCapteur;
import model.TemperatureCapteur;
import model.Type;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class GenCapteursController {

    private static final int ITERATION_LIGHT_CAPTEUR = 3;
    private static final int ITERATION_TEMPERATURE_CAPTEUR = 1;
    private static final int SLEEP_TIME = 1000;

    private String ipDest;
    private int portEnvoi;
    private int portEcoute;
    private List<Capteur> capteurList;
    private ObjectMapper objectMapper;

    public GenCapteursController(String ipDest, int portEnvoi, int portEcoute) {
        this.portEnvoi = portEnvoi;
        this.portEcoute = portEcoute;
        this.objectMapper = new ObjectMapper();
        this.ipDest = ipDest;
    }

    public void start() {
        new SendConfig().sendConfig(portEnvoi, portEcoute, ipDest);
        initCatpeurs();
        initStartStopCapteurControler();
        generateFlux();
    }

    private void initStartStopCapteurControler() {
        StartStopCapteurController startStopCapteurController = new StartStopCapteurController(portEcoute, capteurList);
        Thread thread = new Thread(startStopCapteurController::receptionStartStopFlux);
        thread.start();
    }

    private void initCatpeurs() {
        capteurList = new ArrayList<>();

        LightCapteur lightCapteur = new LightCapteur("LightCapteur", Type.LIGHT, true);
        TemperatureCapteur temperatureCapteur = new TemperatureCapteur("TemperatureCapteur", Type.TEMPERATURE, true);

        capteurList.add(lightCapteur);
        capteurList.add(temperatureCapteur);
    }

    //TODO Génération des flux PAR capteur dans un Thread #15
    private void generateFlux() {
        int i = 0;

        try {
            while (true) {
                for (Capteur capteur : capteurList) {
                    if (capteur.isActivated()) {
                        if (capteur instanceof LightCapteur && i % ITERATION_LIGHT_CAPTEUR == 0)
                            sendFlux(capteur);
                        if (capteur instanceof TemperatureCapteur && i % ITERATION_TEMPERATURE_CAPTEUR == 0)
                            sendFlux(capteur);
                    }
                }
                i++;
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendFlux(Capteur capteur) {
        String capteurJson = "";
        try {
            capteurJson = objectMapper.writeValueAsString(capteur);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DatagramSocket client;

        byte[] buffer = capteurJson.getBytes();

        try {
            client = new DatagramSocket();

            //On crée notre datagramme
            InetAddress adresse = InetAddress.getByName(ipDest);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, portEnvoi);

            //On lui affecte les données à envoyer
            packet.setData(buffer);

            //On envoie au serveur
            client.send(packet);

            System.out.println("Send flux : " + capteur.getName() + " - " + packet.getAddress() + " - " + capteur.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
