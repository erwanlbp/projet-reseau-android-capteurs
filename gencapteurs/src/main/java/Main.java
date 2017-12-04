import controlers.ListenControler;
import controlers.SendListenControler;
import controlers.SendStopControler;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
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