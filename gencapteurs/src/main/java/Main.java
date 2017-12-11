import com.beust.jcommander.JCommander;
import com.google.common.base.Strings;
import controlers.dbinterface.DBInterfaceController;
import controlers.gencapteurs.GenCapteursController;

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
        if (!option.getStartMode().equals("gen-capteur") && !option.getStartMode().equals("db-interface")) {
            System.out.println("start mode can only be gen-capteur or db-interface");
            System.exit(1);
        }

        if (option.getStartMode().equals("gen-capteur")) {
            if (Strings.isNullOrEmpty(option.getIpFirebase()) || option.getPortOut() <= 0 || option.getPortIn() <= 0) {
                System.out.println("gen-capteur needs Firbase ip, port out and port in");
                System.exit(1);
            }
            new GenCapteursController(option.getIpFirebase(), option.getPortOut(), option.getPortIn()).start();
        }

        if (option.getStartMode().equals("db-interface")) {
            if (option.getPortIn() <= 0) {
                System.out.println("db-interface needs port in");
                System.exit(1);
            }
            try {
                new DBInterfaceController(option.getPortIn()).init();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
