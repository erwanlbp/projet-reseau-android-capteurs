package controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Capteur;
import model.LightCapteur;
import model.TemperatureCapteur;
import model.Type;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GenCapteursControler {

    private static final int ITERATION_LIGHT_CAPTEUR = 3;
    private static final int ITERATION_TEMPERATURE_CAPTEUR = 1;
    private static final int SLEEP_TIME = 1000;

    private String ipDest;
    private int portEnvoi;
    private int portEcoute;
    private List<Capteur> capteurList;
    private ObjectMapper objectMapper;

    public GenCapteursControler(String ipDest, int portEnvoi, int portEcoute) {
        this.ipDest = ipDest;
        this.portEnvoi = portEnvoi;
        this.portEcoute = portEcoute;
    }

    public void start() {
        objectMapper = new ObjectMapper();
        initCatpeurs();
        initListenStopControler();
        generateFlux();
    }

    private void initListenStopControler() {
        ListenStopControler listenStopControler = new ListenStopControler(portEcoute, capteurList);
        Thread thread = new Thread(listenStopControler::receivedStopFlux);
        thread.start();
    }

    private void initCatpeurs() {
        capteurList = new ArrayList<>();

        LightCapteur lightCapteur = new LightCapteur("LightCapteur", Type.LIGHT, true);
        TemperatureCapteur temperatureCapteur = new TemperatureCapteur("TemperateurCapteur", Type.TEMPERATURE, true);

        capteurList.add(lightCapteur);
        capteurList.add(temperatureCapteur);
    }

    private void generateFlux() {
        int i = 0;

        try {
            while (true) {
                for (Capteur capteur : capteurList) {
                    if (capteur.isActivated()) {
                        if (capteur instanceof LightCapteur && i % ITERATION_LIGHT_CAPTEUR == 0)
                            prepareFlux(capteur);
                        if (capteur instanceof TemperatureCapteur && i % ITERATION_TEMPERATURE_CAPTEUR == 0)
                            prepareFlux(capteur);
                    }
                }
                i++;
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareFlux(Capteur capteur) {
        double data = capteur.generateData();

        try {
            String s = objectMapper.writeValueAsString(capteur);
            sendFlux(s, capteur.getName(), data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void sendFlux(String capteurJson, String name, double data) {
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

            System.out.println("##### Send flux : " + name + " - " + packet.getAddress() + " - " + data);

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}