import com.beust.jcommander.JCommander;
import controlers.gen_capteurs.GenCapteursController;
import controlers.db_interface.DBInterfaceController;

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
            new GenCapteursController(option.getIpFirebase(), option.getPortOut(), option.getPortIn()).start();
        }

        if (option.getStartMode().equals("db-interface")) {
            try {
                new DBInterfaceController(option.getPortIn()).receiveFlux();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
