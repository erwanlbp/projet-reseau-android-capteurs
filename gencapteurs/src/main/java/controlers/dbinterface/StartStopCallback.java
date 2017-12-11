package controlers.dbinterface;

import model.Capteur;

@FunctionalInterface
public interface StartStopCallback {
    void switchCapteur(Capteur capteur);
}
