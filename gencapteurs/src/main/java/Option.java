import com.beust.jcommander.Parameter;

/**
 * Classe d'option pour les arguments du programme.
 */
public class Option {

    /**
     * Parametre aide.
     */
    @Parameter(names = {"-h", "--help"}, help = true, description = "Affiche l'aide")
    private boolean help;

    /**
     * Param ip de firebase.
     */
    @Parameter(names = {"-ip", "--ip-firebase"}, description = "ip du routeur de destination vers firebase")
    private String ipFirebase = "";

    /**
     * Param port de destination.
     */
    @Parameter(names = {"-pd", "--port-destination"}, description = "port de destination des packets UDP")
    private int portDest = -1;

    /**
     * Param port d'écoute.
     */
    @Parameter(names = {"-pl", "--port-listen"}, description = "port d'écoute du serveur")
    private int portListen = -1;

    /**
     * Param start mode.
     */
    @Parameter(names = {"-m", "--start-mode"}, description = "Mode de lancement [gen-capteur, db-interface]")
    private String startMode = "";

    /**
     * Param network name.
     */
    @Parameter(names = {"-nin", "--network-interface-name"}, description = "Identifiant de l'interface reseau")
    private String networkInterfaceName;

    /**
     * Retourne ip firebase.
     *
     * @return l'IP firebase
     */
    public String getIpFirebase() {
        return ipFirebase;
    }

    /**
     * set ip firebase.
     *
     * @param pIpFirebase string avec l'ip
     */
    public void setIpFirebase(final String pIpFirebase) {
        this.ipFirebase = pIpFirebase;
    }

    /**
     * @return Retourne le port d'écoute
     */
    public int getPortDest() {
        return portDest;
    }

    /**
     * Set le port d'écoute.
     *
     * @param pPortDest port écoute
     */
    public void setPortDest(final int pPortDest) {
        this.portDest = pPortDest;
    }

    /**
     * @return Get port d'écoute
     */
    public int getPortListen() {
        return portListen;
    }

    /**
     * Set port d'écoute.
     *
     * @param pPortListen port d'écoute
     */
    public void setPortListen(final int pPortListen) {
        this.portListen = pPortListen;
    }

    /**
     * Gen start mode.
     *
     * @return start mode
     */
    public String getStartMode() {
        return startMode;
    }

    /**
     * Set start mode.
     *
     * @param mMode mode
     */
    public void setStartMode(final String mMode) {
        this.startMode = mMode;
    }

    /**
     * Get help.
     *
     * @return help
     */
    public boolean getHelp() {
        return help;
    }

    /**
     * Get help.
     *
     * @param hHelp help
     */
    public void setHelp(final boolean hHelp) {
        this.help = hHelp;
    }

    /**
     * Get network.
     *
     * @return le nom de l'interface
     */
    public String getNetworkInterfaceName() {
        return networkInterfaceName;
    }

    /**
     * set network.
     *
     * @param mNetworkInterfaceName network
     */
    public void setNetworkInterfaceName(final String mNetworkInterfaceName) {
        this.networkInterfaceName = mNetworkInterfaceName;
    }
}
