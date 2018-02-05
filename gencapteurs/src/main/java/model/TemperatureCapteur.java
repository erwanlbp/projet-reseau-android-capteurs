package model;

import com.google.firebase.database.Exclude;

/**
 * Capteur de temperature.
 */
public class TemperatureCapteur extends Capteur {
    /**
     * Data courante.
     */
    private double data;

    /**
     * Constructeur par défaut.
     */
    public TemperatureCapteur() {
        super();
    }

    /**
     * Constructeur à utiliser.
     *
     * @param pname      Nom du capteur
     * @param ptype      Type du capteur
     * @param pactivated Si activé
     * @param pdata      Data du capteur
     */
    public TemperatureCapteur(final String pname, final Type ptype, final boolean pactivated, final double pdata) {
        super(pname, ptype, pactivated);
        this.data = pdata;
    }

    /**
     * Pour générer la prochaine data.
     *
     * @return La nouvelle data
     */
    @Override
    public double generateData() {
        data = getFromRange(data, 0.3);
        return data;
    }

    /**
     * Récup la data.
     *
     * @return la data
     */
    @Exclude
    @Override
    public double getData() {
        return this.data;
    }
}
