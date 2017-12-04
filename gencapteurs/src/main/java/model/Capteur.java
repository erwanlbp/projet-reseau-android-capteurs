package model;

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
}