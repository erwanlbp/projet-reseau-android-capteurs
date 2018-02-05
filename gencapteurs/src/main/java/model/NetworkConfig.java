package model;

/**
 * Le singleton pour l'envoi de la config réseau au lancement de l'appli.
 */
public final class NetworkConfig {
    /**
     * Le singleton.
     */
    private static NetworkConfig networkConfig;
    /**
     * L'ip destination à envoyer.
     */
    private String ipDestGenCapteurs;
    /**
     * Le port de destination à envoyer.
     */
    private int portDestGenCapteurs;

    /**
     * Constructeur privé pour le singleton.
     */
    private NetworkConfig() {
        this.ipDestGenCapteurs = "init";
        this.portDestGenCapteurs = -1;
    }

    /**
     * Récup l'instance singleton.
     *
     * @return L'instance
     */
    public static NetworkConfig getInstance() {
        if (networkConfig == null) {
            networkConfig = new NetworkConfig();
        }
        return networkConfig;
    }

    /**
     * L'ip de destination.
     *
     * @return L'ip de destination
     */
    public String getIpDestGenCapteurs() {
        return ipDestGenCapteurs;
    }

    /**
     * L'ip de destination.
     *
     * @param pipDestGenCapteurs L'ip de destination
     */
    public void setIpDestGenCapteurs(final String pipDestGenCapteurs) {
        this.ipDestGenCapteurs = pipDestGenCapteurs;
    }

    /**
     * Le port de destination.
     *
     * @return Le port de destination
     */
    public int getPortDestGenCapteurs() {
        return portDestGenCapteurs;
    }

    /**
     * Le port de destination.
     *
     * @param pportDestGenCapteurs Le port de destination
     */
    public void setPortDestGenCapteurs(final int pportDestGenCapteurs) {
        this.portDestGenCapteurs = pportDestGenCapteurs;
    }

    /**
     * Check si le singleton a été set.
     *
     * @return True si c'ets pas les valeurs par défaut
     */
    public boolean isSet() {
        return !ipDestGenCapteurs.equals("init") && portDestGenCapteurs != -1;
    }
}
