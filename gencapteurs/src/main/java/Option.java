import com.beust.jcommander.Parameter;

public class Option {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Affiche l'aide")
    private boolean help;

    @Parameter(names = {"-ip", "--ip-firebase"}, description = "ip du routeur de destination vers firebase")
    private String ipFirebase = "";

    @Parameter(names = {"-pd", "--port-destination"}, description = "port de destination des packets UDP")
    private int portDest = -1;

    @Parameter(names = {"-pl", "--port-listen"}, description = "port d'écoute du serveur")
    private int portListen = -1;

    @Parameter(names = {"-m", "--start-mode"}, description = "Mode de lancement [gen-capteur, db-interface]")
    private String startMode = "";

    public String getIpFirebase() {
        return ipFirebase;
    }

    public void setIpFirebase(String ipFirebase) {
        this.ipFirebase = ipFirebase;
    }

    public int getPortDest() {
        return portDest;
    }

    public void setPortDest(int portDest) {
        this.portDest = portDest;
    }

    public int getPortListen() {
        return portListen;
    }

    public void setPortListen(int portListen) {
        this.portListen = portListen;
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
}
