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

        checkMode(option.getStartMode());

        if (option.getStartMode().equals("gen-capteur")) {
            checkForGenCapteur(option);
            new GenCapteursController(option.getIpFirebase(), option.getPortDest(), option.getPortListen(), option.getNetworkInterfaceName()).start();
        }

        if (option.getStartMode().equals("db-interface")) {
            checkDBInterface(option);
            try {
                new DBInterfaceController(option.getPortListen()).init();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * Check les options pour lancer en db-interface.
     *
     * @param option Les options
     */
    private static void checkDBInterface(final Option option) {
        if (option.getPortListen() <= 0) {
            System.out.println("db-interface needs port listen");
            System.exit(1);
        }
    }

    /**
     * Check les options pour lancer en gen-capteur.
     *
     * @param option les options
     */
    private static void checkForGenCapteur(final Option option) {
        if (Strings.isNullOrEmpty(option.getIpFirebase()) || option.getPortDest() <= 0 || option.getPortListen() <= 0 || Strings.isNullOrEmpty(option.getNetworkInterfaceName())) {
            System.out.println("gen-capteur needs Firbase ip, port destination and port listen");
            System.exit(1);
        }
    }

    /**
     * Check le mode.
     *
     * @param mode mode de lancement
     */
    private static void checkMode(final String mode) {
        if (!mode.equals("gen-capteur") && !mode.equals("db-interface")) {
            System.out.println("start mode can only be gen-capteur or db-interface");
            System.exit(1);
        }
    }
}
