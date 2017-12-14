package model;

import com.google.firebase.database.Exclude;

public class TemperatureCapteur extends Capteur {

    private double data;

    public TemperatureCapteur() {
        super();
    }

    public TemperatureCapteur(String name, Type type, boolean activated, double data) {
        super(name, type, activated);
        this.data = data;
    }

    @Override
    public double generateData() {
        data = getFromRange(data, 0.3);
        return data;
    }

    @Exclude
    @Override
    public double getData() {
        return this.data;
    }
}
