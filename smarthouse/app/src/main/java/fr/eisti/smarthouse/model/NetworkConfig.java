package fr.eisti.smarthouse.model;

public class NetworkConfig {
    private String ipDestGenCapteurs;
    private int portDestGenCapteurs;

    private static NetworkConfig networkConfig;

    public NetworkConfig() {}

    public NetworkConfig(String ipDestGenCapteurs, int portDestGenCapteurs) {
        this.ipDestGenCapteurs = ipDestGenCapteurs;
        this.portDestGenCapteurs = portDestGenCapteurs;
    }

    public static void setNetworkConfig(NetworkConfig networkConf) {
         networkConfig = networkConf;
    }

    public static NetworkConfig getNetworkConfig() {
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
}
