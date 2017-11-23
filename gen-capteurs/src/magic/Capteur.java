package magic;

public abstract class Capteur {
    protected String name;
    protected String type;
    protected boolean activated;

    protected abstract void prepare(String dest);
    protected abstract void generate();
}