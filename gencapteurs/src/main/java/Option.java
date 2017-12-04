import com.beust.jcommander.Parameter;

public class Option {

    @Parameter(names = "-ip-firebase", description = "ip destination firebase")
    private String ip;

    @Parameter(names = "-port-src", description = "port Envoi")
    private int portEnvoi;

    @Parameter(names = "-port-ecoute", description = "port Ecoute")
    private int portEcoute;

    @Parameter(names = "-mode", description = "Modes :\\n\" +\n" +
            "                    \"- gen-capteur : Générer les flux et écouter les commandes de start et stop\\n\" +\n" +
            "                    \"- capteurs : Ecoute les flux et persiste dans firebase\\n\" +\n" +
            "                    \"- stop : Envoyer commande de stop du LightCapteur\\n\"")
    private String mode;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPortEnvoi() {
        return portEnvoi;
    }

    public void setPortEnvoi(int portEnvoi) {
        this.portEnvoi = portEnvoi;
    }

    public int getPortEcoute() {
        return portEcoute;
    }

    public void setPortEcoute(int portEcoute) {
        this.portEcoute = portEcoute;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
