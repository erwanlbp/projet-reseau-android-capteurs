import com.beust.jcommander.Parameter;

public class Option {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Affiche l'aide")
    private boolean help;

    @Parameter(names = {"-ip", "--ip-firebase"}, description = "ip du routeur de destination vers firebase")
    private String ipFirebase = "";

    @Parameter(names = {"-po", "--port-out"}, description = "port de sortie du serveur")
    private int portOut = -1;

    @Parameter(names = {"-pi", "--port-in"}, description = "port d'écoute du serveur")
    private int portIn = -1;

    @Parameter(names = {"-m", "--start-mode"}, description = "Mode de lancement [gen-capteur, db-interface]")
    private String startMode = "";

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
}
