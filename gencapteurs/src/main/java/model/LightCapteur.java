package model;

import com.google.firebase.database.Exclude;

import java.util.Random;

public class LightCapteur extends Capteur {

    private double data;

    public LightCapteur() {
        super();
    }

    public LightCapteur(String name, Type type, boolean activated) {
        super(name, type, activated);
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
}
