package fr.eisti.smarthouse.provider.callback;

import fr.eisti.smarthouse.model.Capteur;

@FunctionalInterface
public interface FirebaseFindOneCallback {
    void populate(Capteur capteur);
}
