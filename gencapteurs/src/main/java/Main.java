import controlers.ReceivedControler;
import controlers.SendControler;

public class Main {

    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String mode = args[2];

        if (mode.equals("send")) {
            SendControler sendControler = new SendControler(ip, port);
            sendControler.start();
        }
        if (mode.equals("listen")) {
            ReceivedControler receivedControler = new ReceivedControler(port);
            receivedControler.start();
        }
    }
}