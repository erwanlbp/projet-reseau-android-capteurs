package model;

import com.google.firebase.database.DataSnapshot;

public class Capteur {

    private String name;
    private Type type;
    private boolean activated;

    public Capteur() {}

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

    @Override
    public String toString() {
        return "Capteur{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", activated=" + activated +
                '}';
    }

    public static Capteur fromDataSnapshot(DataSnapshot dataSnapshot) {
        Capteur capteur = new Capteur();
        capteur.setName((String) dataSnapshot.child("name").getValue());

        Type newType = Type.LIGHT;
        switch (dataSnapshot.child("type").getValue().toString()) {
            case "LIGHT":
                newType = Type.LIGHT;
                break;
            case "TEMPERATURE":
                newType = Type.TEMPERATURE;
                break;
        }

        capteur.setType(newType);
        capteur.setActivated((boolean) dataSnapshot.child("activ").getValue());
        return capteur;
    }
}