package model;

import com.google.firebase.database.Exclude;

import java.util.Random;

public class TemperatureCapteur extends Capteur {

    private double data;

    public TemperatureCapteur() {
        super();
    }

    public TemperatureCapteur(String name, Type type, boolean activated) {
        super(name, type, activated);
    }

    @Override
    public double generateData() {
        Random r = new Random();
        this.data = 0 + (50) * r.nextDouble();
        return data;
    }

    @Exclude
    @Override
    public double getData() {
        return this.data;
    }
}
