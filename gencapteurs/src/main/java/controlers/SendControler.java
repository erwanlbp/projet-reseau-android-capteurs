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

public class SendControler {

    private static final int ITERATION_LIGHT_CAPTEUR = 3;
    private static final int ITERATION_TEMPERATURE_CAPTEUR = 1;
    private static final int SLEEP_TIME = 1000;

    private String ipDest;
    private int port;
    private List<Capteur> capteurList;
    private ObjectMapper objectMapper;

    public SendControler(String ipDest, int port) {
        this.ipDest = ipDest;
        this.port = port;
    }

    public void start() {
        objectMapper = new ObjectMapper();
        initCatpeurs();
        generateFlux();
    }

    private void initCatpeurs() {
        capteurList = new ArrayList<Capteur>();

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
                    if (capteur instanceof LightCapteur && i % ITERATION_LIGHT_CAPTEUR == 0)
                        prepareFlux((LightCapteur) capteur);
                    if (capteur instanceof TemperatureCapteur && i % ITERATION_TEMPERATURE_CAPTEUR == 0)
                        prepareFlux((TemperatureCapteur) capteur);
                }
                i++;
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prepareFlux(LightCapteur capteur) {
        capteur.generateData();

        try {
            String s = objectMapper.writeValueAsString(capteur);
            sendFlux(s, capteur.getName(), capteur.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void prepareFlux(TemperatureCapteur capteur) {
        capteur.generateData();

        try {
            String s = objectMapper.writeValueAsString(capteur);
            sendFlux(s, capteur.getName(), capteur.getData());
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
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);

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