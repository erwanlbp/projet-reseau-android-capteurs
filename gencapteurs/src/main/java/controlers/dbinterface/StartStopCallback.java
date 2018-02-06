package controlers.dbinterface;

import model.Capteur;

/**
 * L'interface pour les fonctions lambda Firebase.
 */
@FunctionalInterface
public interface StartStopCallback {
    /**
     * Pour activer ou désactiver un capteur.
     *
     * @param capteur Le capteur à activer/désactiver
     */
    void switchCapteur(Capteur capteur);
}
