import controlers.ListenControler;
import controlers.SendListenControler;
import controlers.SendStopControler;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Si on a pas d'argument ou si le premier est "help" afficher help
        if (args.length == 0 || args[0].equals("help")) {
            System.out.println("#### HELP ####");
            System.out.println("Commande à lancer : java -jar <jarname> <ip> <mode>");
            System.out.println("Modes :");
            System.out.println("- send/listen : Générer les flux et écouter les commandes de start et stop");
            System.out.println("- listen : Ecouter les commandes de start et stop");
            System.out.println("- stop : Envoyer commande de stop d'un LightCapteur");
            return;
        }

        String ip = args[0];
        int portEnvoi = Integer.parseInt(args[1]);
        int portEcoute = Integer.parseInt(args[2]);
        String mode = args[3];

        if (mode.equals("send/listen")) {
            SendListenControler sendListenControler = new SendListenControler(ip, portEnvoi, portEcoute);
            sendListenControler.start();
        }
        if (mode.equals("listen")) {
            ListenControler listenControler = new ListenControler(portEcoute);
            listenControler.start();
            listenControler.receivedFlux();
        }
        if (mode.equals("stop")) {
            SendStopControler sendStopControler = new SendStopControler(ip, portEnvoi);
            sendStopControler.start();
        }
    }
}