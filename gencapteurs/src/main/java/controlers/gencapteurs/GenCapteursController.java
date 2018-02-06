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

/**
 * Pour lancer le programme en mode réseau de capteur.
 */
public class GenCapteursController {

    /**
     * Le taux d'envoi de données pour light.
     */
    private static final int ITERATION_LIGHT_CAPTEUR = 3;
    /**
     * Le taux d'envoi de données pour temperature.
     */
    private static final int ITERATION_TEMPERATURE_CAPTEUR = 2;
    /**
     * Le temps de pause entre les envoi de données.
     */
    private static final int SLEEP_TIME = 500;

    /**
     * L'ip vers qui envoyer les data.
     */
    private String ipDest;
    /**
     * Le port de destination des data.
     */
    private int portDest;
    /**
     * Le port d'écoute des message start stop.
     */
    private int portListen;
    /**
     * Le pattern pour savoir sur quel interface parler.
     */
    private String networkInterfaceName;
    /**
     * La liste des capteurs.
     */
    private List<Capteur> capteurList;
    /**
     * Pour formatter et parser les capteurs en JSON.
     */
    private ObjectMapper objectMapper;

    /**
     * Constructeur à utiliser.
     *
     * @param pipDest               L'ip de destination
     * @param pportDest             Le port de destination
     * @param pportListen           Le port d'écoute
     * @param pnetworkInterfaceName L'interface sur laquelle parler
     */
    public GenCapteursController(final String pipDest, final int pportDest, final int pportListen, final String pnetworkInterfaceName) {
        this.portDest = pportDest;
        this.portListen = pportListen;
        this.objectMapper = new ObjectMapper();
        this.ipDest = pipDest;
        this.networkInterfaceName = pnetworkInterfaceName;
    }

    /**
     * Pour lancer le mode.
     */
    public void start() {
        new SendConfig().send(portDest, portListen, ipDest, networkInterfaceName);
        initCatpeurs();
        initStartStopCapteurControler();
        generateFlux();
    }

    /**
     * Pour initialiser le start/stop.
     */
    private void initStartStopCapteurControler() {
        StartStopCapteurController startStopCapteurController = new StartStopCapteurController(portListen, capteurList);
        Thread thread = new Thread(startStopCapteurController::receptionStartStopFlux);
        thread.start();
    }

    /**
     * Pour initialiser les capteurs du réseau.
     */
    private void initCatpeurs() {
        capteurList = new ArrayList<>();

        LightCapteur luxCave1 = new LightCapteur("Cave 1", Type.LIGHT, true, 500);
        TemperatureCapteur tempCave1 = new TemperatureCapteur("Cave 1", Type.TEMPERATURE, true, 5);

        capteurList.add(luxCave1);
        capteurList.add(tempCave1);
    }

    /**
     * Pour lancer la boucle de génération des flux.
     */
    //TODO Génération des flux PAR capteur dans un Thread #15
    private void generateFlux() {
        int i = 0;

        try {
            while (true) {
                for (Capteur capteur : capteurList) {
                    if (capteur.isActivated()) {
                        capteur.generateData();
                        if (capteur instanceof LightCapteur && i % ITERATION_LIGHT_CAPTEUR == 0) {
                            sendFlux(capteur);
                        }
                        if ((capteur instanceof TemperatureCapteur) && (i % ITERATION_TEMPERATURE_CAPTEUR == 0)) {
                            sendFlux(capteur);
                        }
                    }
                }
                i++;
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pour envoyer un flux.
     *
     * @param capteur Le capteur à envoyer
     */
    private void sendFlux(final Capteur capteur) {
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
