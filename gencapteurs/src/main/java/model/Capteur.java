package model;

import com.google.firebase.database.DataSnapshot;

import java.util.Random;

/**
 * Classe mère de données des capteurs.
 */
public abstract class Capteur implements HandleData {

    /**
     * Le nom du capteur.
     */
    private String name;

    /**
     * L'énumération du type de capteur.
     */
    private Type type;

    /**
     * Si le capteur est activé.
     */
    private boolean activated;

    /**
     * Constructeur à utiliser.
     *
     * @param pname      Nom du capteur
     * @param ptype      Type du capteur
     * @param pactivated Si il est activé ou pas
     */
    public Capteur(final String pname, final Type ptype, final boolean pactivated) {
        this.name = pname;
        this.type = ptype;
        this.activated = pactivated;
    }

    /**
     * Constructeur par défaut.
     */
    public Capteur() {

    }

    /**
     * Récupère un capteur à partir d'un snapshot firebase.
     *
     * @param dataSnapshot Le snapshot firebase
     * @return Un capteur
     */
    public static Capteur fromSnapshot(final DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if (ds.getValue().equals(Type.LIGHT.toString())) {
                return dataSnapshot.getValue(LightCapteur.class);
            }
            if (ds.getValue().equals(Type.TEMPERATURE.toString())) {
                return dataSnapshot.getValue(TemperatureCapteur.class);
            }
        }
        System.out.println("Classe de capteur inconnue");
        return null;
    }

    /**
     * Le nom du capteur.
     *
     * @return Le nom du capteur
     */
    public String getName() {
        return this.name;
    }

    /**
     * Le nom du capteur.
     *
     * @param pname Le nom du capteur
     */
    public void setName(final String pname) {
        this.name = pname;
    }

    /**
     * Le type de capteur.
     *
     * @return Le type de capteur
     */
    public Type getType() {
        return type;
    }

    /**
     * Le type de capteur.
     *
     * @param ptype Le type de capteur
     */
    public void setType(final Type ptype) {
        this.type = ptype;
    }

    /**
     * Si le capteur est activé.
     *
     * @return True si le capteur est activé
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Active ou désactive le capteur.
     *
     * @param pactivated Activé ou non
     */
    public void setActivated(final boolean pactivated) {
        this.activated = pactivated;
    }

    /**
     * Renvoi un random à partir d'une data dans une range.
     *
     * @param data  La data précédente
     * @param marge La marge de variation
     * @return Une valeur dans [data-marge ; data+marge]
     */
    protected double getFromRange(final double data, final double marge) {
        Random r = new Random();
        double min = data - marge;
        double max = data + marge;
        return Math.floor((min + (max - min) * r.nextDouble()) * 100) / 100;
    }

    /**
     * Joli format.
     *
     * @return La string bien formatté
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + name + "] " + type + " " + (activated ? "ON" : "OFF") + " " + getData();
    }
}
