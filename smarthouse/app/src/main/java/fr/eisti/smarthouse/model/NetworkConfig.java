package fr.eisti.smarthouse.model;

public class NetworkConfig {
    private String ip_dest_gen_capteurs;
    private String port_dest_gen_capteurs;

    public NetworkConfig() {}

    public NetworkConfig(String ip_dest_gen_capteurs, String port_dest_gen_capteurs) {
        this.ip_dest_gen_capteurs = ip_dest_gen_capteurs;
        this.port_dest_gen_capteurs = port_dest_gen_capteurs;
    }

    public String getIp_dest_gen_capteurs() {
        return ip_dest_gen_capteurs;
    }

    public void setIp_dest_gen_capteurs(String ip_dest_gen_capteurs) {
        this.ip_dest_gen_capteurs = ip_dest_gen_capteurs;
    }

    public String getPort_dest_gen_capteurs() {
        return port_dest_gen_capteurs;
    }

    public void setPort_dest_gen_capteurs(String port_dest_gen_capteurs) {
        this.port_dest_gen_capteurs = port_dest_gen_capteurs;
    }
}
