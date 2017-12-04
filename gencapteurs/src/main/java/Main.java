import com.beust.jcommander.JCommander;
import controlers.CapteursControler;
import controlers.GenCapteursControler;

public class Main {

    public static void main(String[] args) {
        Option option = new Option();
        JCommander commander = JCommander.newBuilder()
            .addObject(option)
            .build();
        commander.parse(args);

        if (option.getHelp()) {
            commander.usage();
            return;
        }

        if (option.getMode().equals("gen-capteur")) {
            GenCapteursControler genCapteursControler = new GenCapteursControler(option.getIpFirebase(), option.getPortOut(), option.getPortIn());
            genCapteursControler.start();
        }
        if (option.getMode().equals("db-interface")) {
            CapteursControler capteursControler = new CapteursControler(option.getPortIn());
            try {
                capteursControler.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            capteursControler.receivedFlux();
        }
    }
}