import com.beust.jcommander.JCommander;
import com.google.common.base.Strings;
import controlers.dbinterface.DBInterfaceController;
import controlers.gencapteurs.GenCapteursController;

/**
 * le point d'entrée.
 */
public abstract class Main {

    /**
     * A ton avis?
     *
     * @param args les arguments
     */
    public static void main(final String[] args) {
        Option option = new Option();
        JCommander commander = JCommander.newBuilder()
            .addObject(option)
            .build();
        commander.parse(args);

        if (option.getHelp()) {
            commander.usage();
            return;
        }
        if (!option.getStartMode().equals("gen-capteur") && !option.getStartMode().equals("db-interface")) {
            System.out.println("start mode can only be gen-capteur or db-interface");
            System.exit(1);
        }

        if (option.getStartMode().equals("gen-capteur")) {
            if (Strings.isNullOrEmpty(option.getIpFirebase()) || option.getPortDest() <= 0 || option.getPortListen() <= 0 || Strings.isNullOrEmpty(option.getNetworkInterfaceName())) {
                System.out.println("gen-capteur needs Firbase ip, port destination and port listen");
                System.exit(1);
            }
            new GenCapteursController(option.getIpFirebase(), option.getPortDest(), option.getPortListen(), option.getNetworkInterfaceName()).start();
        }

        if (option.getStartMode().equals("db-interface")) {
            if (option.getPortListen() <= 0) {
                System.out.println("db-interface needs port listen");
                System.exit(1);
            }
            try {
                new DBInterfaceController(option.getPortListen()).init();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
