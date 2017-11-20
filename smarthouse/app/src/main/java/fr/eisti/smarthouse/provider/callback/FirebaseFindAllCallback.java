package fr.eisti.smarthouse.provider.callback;

import java.util.List;

import fr.eisti.smarthouse.model.Capteur;

/**
 * Created by ErwanLBP on 20/11/17.
 */

@FunctionalInterface
public interface FirebaseFindAllCallback {
    void populate(List<Capteur> capteurs);
}

