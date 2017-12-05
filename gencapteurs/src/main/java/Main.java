import com.beust.jcommander.JCommander;
import controlers.CapteursFluxGenerationController;
import controlers.CapteursNetworkController;

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

        if (option.getStartMode().equals("gen-capteur")) {
            new CapteursFluxGenerationController(option.getIpFirebase(), option.getPortOut(), option.getPortIn()).start();
        }

        if (option.getStartMode().equals("db-interface")) {
            try {
                new CapteursNetworkController(option.getPortIn()).receiveFlux();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
