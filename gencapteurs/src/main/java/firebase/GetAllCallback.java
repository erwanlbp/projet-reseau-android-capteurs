package firebase;

import model.Capteur;

import java.util.List;

public interface GetAllCallback {
    void retrieve(List<Capteur> list);
}
