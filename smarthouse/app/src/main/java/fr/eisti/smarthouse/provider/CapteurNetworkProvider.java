package fr.eisti.smarthouse.provider;

import android.app.Activity;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import fr.eisti.smarthouse.model.NetworkConfig;

/**
 * Created by ErwanLBP on 30/11/17.
 */

public class CapteurNetworkProvider {

    private static final String TAG = "CapteurNetworkProvider";

    public static void switchActiv(Activity activity, String capteurName, boolean activ) {
        if (capteurName == null) {
            Toast.makeText(activity, "Can't activate capteur because name is null", Toast.LENGTH_SHORT).show();
            return;
        }

        int serverPort = NetworkConfig.getNetworkConfig().getPortDestGenCapteurs();
        String msg = formSwitchActivMessage(capteurName, activ);
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();

        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress server = InetAddress.getByName(NetworkConfig.getNetworkConfig().getIpDestGenCapteurs());
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
