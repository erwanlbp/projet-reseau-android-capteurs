package controlers.gencapteurs;

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
    private static final int SLEEP_TIME = 500;

    private String ipDest;
    private int portDest;
    private int portListen;
    private String networkInterfaceName;
    private List<Capteur> capteurList;
    private ObjectMapper objectMapper;

    public GenCapteursController(String ipDest, int portDest, int portListen, String networkInterfaceName) {
        this.portDest = portDest;
        this.portListen = portListen;
        this.objectMapper = new ObjectMapper();
        this.ipDest = ipDest;
        this.networkInterfaceName = networkInterfaceName;
    }

    public void start() {
        new SendConfig().send(portDest, portListen, ipDest, networkInterfaceName);
        initCatpeurs();
        initStartStopCapteurControler();
        generateFlux();
    }

    private void initStartStopCapteurControler() {
        StartStopCapteurController startStopCapteurController = new StartStopCapteurController(portListen, capteurList);
        Thread thread = new Thread(startStopCapteurController::receptionStartStopFlux);
        thread.start();
    }

    private void initCatpeurs() {
        capteurList = new ArrayList<>();

        LightCapteur luxCave1 = new LightCapteur("Cave 1", Type.LIGHT, true, 500);
        LightCapteur luxCave2 = new LightCapteur("Cave 2", Type.LIGHT, true, 400);
        LightCapteur luxSalon1 = new LightCapteur("Salon 1", Type.LIGHT, true, 1300);
        LightCapteur luxSalon2 = new LightCapteur("salon 2", Type.LIGHT, true, 1300);
        LightCapteur luxVeranda1 = new LightCapteur("Veranda 1", Type.LIGHT, true, 1800);
        LightCapteur luxVeranda2 = new LightCapteur("Veranda 2", Type.LIGHT, true, 2000);
        TemperatureCapteur tempCave1 = new TemperatureCapteur("Cave 1", Type.TEMPERATURE, true, 5);
        TemperatureCapteur tempCave2 = new TemperatureCapteur("Cave 2", Type.TEMPERATURE, true, 4);
        TemperatureCapteur tempTerasse1 = new TemperatureCapteur("Terasse 1", Type.TEMPERATURE, true, -5);
        TemperatureCapteur tempTerasse2 = new TemperatureCapteur("Terasse 2", Type.TEMPERATURE, true, -6);
        TemperatureCapteur tempCheminee = new TemperatureCapteur("Cheminee", Type.TEMPERATURE, true, 28);
        TemperatureCapteur tempSalon = new TemperatureCapteur("Salon", Type.TEMPERATURE, true, 20);

        capteurList.add(luxCave1);
        capteurList.add(luxCave2);
        capteurList.add(luxSalon1);
        capteurList.add(luxSalon2);
        capteurList.add(luxVeranda1);
        capteurList.add(luxVeranda2);
        capteurList.add(tempCave1);
        capteurList.add(tempCave2);
        capteurList.add(tempCheminee);
        capteurList.add(tempSalon);
        capteurList.add(tempTerasse1);
        capteurList.add(tempTerasse2);
    }

    //TODO Génération des flux PAR capteur dans un Thread #15
    private void generateFlux() {
        int i = 0;

        try {
            while (true) {
                for (Capteur capteur : capteurList) {
                    if (capteur.isActivated()) {
                        capteur.generateData();
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
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, portDest);

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
