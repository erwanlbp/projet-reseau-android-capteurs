package model;

import com.google.firebase.database.Exclude;

import java.util.Random;

public class LightCapteur extends Capteur {

    private double data;

    public LightCapteur() {
    }

    public LightCapteur(String name, Type type, boolean activated) {
        super(name, type, activated);
    }

    public LightCapteur(String name, Type type, boolean activated, double data) {
        super(name, type, activated);
        this.data = data;
    }

    @Override
    public double generateData() {
        Random r = new Random();
        this.data = 0 + (1000) * r.nextDouble();
        return data;
    }

    @Exclude
    @Override
    public double getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "LightCapteur{" +
                "name='" + getName() + '\'' +
                ", type=" + getType() +
                ", activated=" + isActivated() +
                ", data=" + data +
                '}';
    }
}