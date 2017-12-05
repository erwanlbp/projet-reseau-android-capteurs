package fr.eisti.smarthouse.provider;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by ErwanLBP on 30/11/17.
 */

public class CapteurNetworkProvider {

    private static final String TAG = "FirebaseCapteurProvider";


    public static void switchActiv(Activity activity, String capteurName, boolean activ) {
        if (capteurName == null) {
            Toast.makeText(activity, "Can't activate capteur because name is null", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO Recupérer le port d'écoute et l'adresse du serveur pour l'appli #10
        int serverPort = 7777;
        String msg = formSwitchActivMessage(capteurName, activ);
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();

        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress server = InetAddress.getByName("172.20.10.2");
            int msgLength = msg.length();
            byte[] msgByte = msg.getBytes();
            DatagramPacket dp = new DatagramPacket(msgByte, msgLength, server, serverPort);
            ds.send(dp);
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static String formSwitchActivMessage(String capteurName, boolean activ) {
        return capteurName + " : " + activ;
    }

}
