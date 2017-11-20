package fr.eisti.smarthouse.model;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class Capteur {
    public static final String NAME = "name";
    private String name;
    public static final String TYPE = "type";
    private String type;
    public static final String ACTIV = "activ";
    private boolean activ;

    public Capteur(String name, String type, boolean activ) {
        this.name = name;
        this.type = type;
        this.activ = activ;
    }

    public Capteur() {
        this(null, null, false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    @Override
    public String toString() {
        return "[" + name + "] " + type + " " + (activ ? "ON" : "OFF");
    }

    public static Capteur fromDataSnapshot(DataSnapshot dataSnapshot) {
        Capteur capteur = new Capteur();
        capteur.setName((String) dataSnapshot.child(NAME).getValue());
        capteur.setType((String) dataSnapshot.child(TYPE).getValue());
        capteur.setActiv((boolean) dataSnapshot.child(ACTIV).getValue());
        return capteur;
    }
}
