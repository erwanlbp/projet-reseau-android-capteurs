package model;

public class NetworkConfig {
    private String ipDestGenCapteurs;
    private int portDestGenCapteurs;

    private static NetworkConfig networkConfig;

    private NetworkConfig() {
        this.ipDestGenCapteurs = "init";
        this.portDestGenCapteurs = -1;
    }

    public static NetworkConfig getInstance() {
        if (networkConfig == null)
            networkConfig = new NetworkConfig();
        return networkConfig;
    }

    public String getIpDestGenCapteurs() {
        return ipDestGenCapteurs;
    }

    public void setIpDestGenCapteurs(String ipDestGenCapteurs) {
        this.ipDestGenCapteurs = ipDestGenCapteurs;
    }

    public int getPortDestGenCapteurs() {
        return portDestGenCapteurs;
    }

    public void setPortDestGenCapteurs(int portDestGenCapteurs) {
        this.portDestGenCapteurs = portDestGenCapteurs;
    }

    public boolean isSet() {
        return !ipDestGenCapteurs.equals("init") && portDestGenCapteurs != -1;
    }
}
