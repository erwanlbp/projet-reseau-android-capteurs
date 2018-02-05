package model;

/**
 * Class handledata.
 */
public interface HandleData {

    /**
     * Genere les données random.
     * @return
     */
    double generateData();

    /**
     * Retourne la valeur de la données du capteur.
     * @return
     */
    double getData();
}
