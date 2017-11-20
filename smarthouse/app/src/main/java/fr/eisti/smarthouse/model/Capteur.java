package fr.eisti.smarthouse.model;

import android.content.ContentValues;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class Capteur {
    public static final String NAME = "NAME";
    private String name;
    public static final String TYPE = "TYPE";
    private String type;
    public static final String ACTIV = "ACTIV";
    private boolean activ;

    public Capteur(String name, String type, boolean activ) {
        this.name = name;
        this.type = type;
        this.activ = activ;
    }

    public Capteur() {
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

    public ContentValues asContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(TYPE, type);
        contentValues.put(ACTIV, activ);
        return contentValues;
    }

    public static Capteur fromContentValues(ContentValues contentValues) {
        if (contentValues == null)
            return null;
        return new Capteur(contentValues.getAsString(NAME), contentValues.getAsString(TYPE), contentValues.getAsBoolean(ACTIV));
    }
}
