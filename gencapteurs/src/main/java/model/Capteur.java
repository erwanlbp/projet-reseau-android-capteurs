package model;

import com.google.firebase.database.DataSnapshot;

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

    public String getName() {
        return name;
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

    public static Capteur fromSnapshot(DataSnapshot dataSnapshot) {
        switch (dataSnapshot.getKey()) {
            case "LightCapteur":
                return dataSnapshot.getValue(LightCapteur.class);
            case "TemperatureCapteur":
                return dataSnapshot.getValue(TemperatureCapteur.class);
            default:
                System.out.println("Classe de capteur inconnue");
                return null;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + name + "] " + type + " " + (activated ? "ON" : "OFF") + " " + getData();
    }
}
