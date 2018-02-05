package model;

import com.google.firebase.database.DataSnapshot;

import java.util.Random;

/**
 * Classe de données
 */
public abstract class Capteur implements HandleData {

    private String name;
    private Type type;
    private boolean activated;

    public Capteur() {
    }

    public Capteur(String name, Type type, boolean activated) {
        this.name = name;
        this.type = type;
        this.activated = activated;
    }

    public static Capteur fromSnapshot(DataSnapshot dataSnapshot) {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    protected double getFromRange(double data, double marge) {
        Random r = new Random();
        double min = data - marge;
        double max = data + marge;
        return Math.floor((min + (max - min) * r.nextDouble()) * 100) / 100;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + name + "] " + type + " " + (activated ? "ON" : "OFF") + " " + getData();
    }
}
