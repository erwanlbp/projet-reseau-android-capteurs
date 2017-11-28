package model;

import java.util.Random;

public class LightCapteur extends Capteur {

    private double data;

    public LightCapteur() {}

    public LightCapteur(String name, Type type, boolean activated) {
        super(name, type, activated);
    }

    public LightCapteur(String name, Type type, boolean activated, double data) {
        super(name, type, activated);
        this.data = data;
    }

    public void generateData() {
        Random r = new Random();
        this.data = 0 + (1000) * r.nextDouble();
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
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
