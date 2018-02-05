package model;

import com.google.firebase.database.Exclude;

/**
 * Classe de capteur de lumiere.
 */
public class LightCapteur extends Capteur {

    /**
     * Range pour générer data.
     */
    private static final int RANGE = 3;

    /**
     * Valeur du capteur.
     */
    private double data;

    /**
     * Constructeur vide.
     */
    public LightCapteur() {
        super();
    }

    /**
     * Constructeur.
     *
     * @param name      nom du capteur
     * @param type      type du capteur
     * @param activated si il est actif ou non
     * @param pdata      données d'initialisation
     */
    public LightCapteur(final String name, final Type type, final boolean activated, final double pdata) {
        super(name, type, activated);
        this.data = pdata;
    }

    /**
     * Genere les data aleatoirement.
     * @return
     */
    @Override
    public double generateData() {
        data = getFromRange(data, RANGE);
        return data;
    }

    /**
     * Getter pour la données.
     * @return
     */
    @Exclude
    @Override
    public double getData() {
        return this.data;
    }
}
