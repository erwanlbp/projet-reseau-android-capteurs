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
    public static final String ACTIV = "activated";
    private boolean activated;

    public Capteur(String name, String type, boolean activated) {
        this.name = name;
        this.type = type;
        this.activated = activated;
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "[" + name + "] " + type + " " + (activated ? "ON" : "OFF");
    }

    public static Capteur fromDataSnapshot(DataSnapshot dataSnapshot) {
        Capteur capteur = new Capteur();
        capteur.setName((String) dataSnapshot.child(NAME).getValue());
        capteur.setType((String) dataSnapshot.child(TYPE).getValue());
        capteur.setActivated((boolean) dataSnapshot.child(ACTIV).getValue());
        return capteur;
    }
}
