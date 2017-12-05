import com.beust.jcommander.JCommander;
import controlers.CapteursController;
import controlers.GenCapteursController;

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
            GenCapteursController genCapteursController = new GenCapteursController(option.getIpFirebase(), option.getPortOut(), option.getPortIn());
            genCapteursController.start();
        }
        if (option.getMode().equals("db-interface")) {
            CapteursController capteursController = new CapteursController(option.getPortIn());
            try {
                capteursController.start();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            capteursController.receivedFlux();
        }
    }
}
