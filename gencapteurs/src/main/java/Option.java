import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class Option {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Affiche l'aide")
    private boolean help;

    @Parameter(names = "-ip-firebase", description = "ip du routeur de destination vers firebase")
    private String ipFirebase;

    @Parameter(names = "-port-out", description = "port de sortie du serveur")
    private int portOut;

    @Parameter(names = "-port-in", required = true, description = "port d'écoute du serveur")
    private int portIn;

    @Parameter(names = "-start-mode", required = true, validateWith = StartModeValidation.class, description = "Mode de lancement [gen-capteur, db-interface]")
    private String startMode;

    public String getIpFirebase() {
        return ipFirebase;
    }

    public void setIpFirebase(String ipFirebase) {
        this.ipFirebase = ipFirebase;
    }

    public int getPortOut() {
        return portOut;
    }

    public void setPortOut(int portOut) {
        this.portOut = portOut;
    }

    public int getPortIn() {
        return portIn;
    }

    public void setPortIn(int portIn) {
        this.portIn = portIn;
    }

    public String getStartMode() {
        return startMode;
    }

    public void setStartMode(String mode) {
        this.startMode = mode;
    }

    public boolean getHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    private class StartModeValidation implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            if (!value.equals("gen-capteur") && !value.equals("db-interface"))
                throw new ParameterException("Parameter " + name + " should be equal to gen-capteur or db-interface");
        }
    }
}
