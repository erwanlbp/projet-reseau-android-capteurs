import com.beust.jcommander.JCommander;
import controlers.CapteursControler;
import controlers.GenCapteursControler;
import controlers.StopControler;

public class Main {

    public static void main(String[] args) {
        Option option = new Option();

        JCommander.newBuilder()
                .addObject(option)
                .build()
                .parse(argv);

        if (option.getMode().equals("gen-capteur")) {
            GenCapteursControler genCapteursControler = new GenCapteursControler(option.getIp(), option.getPortEnvoi(), option.getPortEcoute());
            genCapteursControler.start();
        }
        if (mode.equals("capteurs")) {
            CapteursControler capteursControler = new CapteursControler(portEcoute);
            capteursControler.start();
            capteursControler.receivedFlux();
        }
        if (mode.equals("stop")) {
            StopControler stopControler = new StopControler(ip, portEnvoi);
            stopControler.start();
        }
    }
}