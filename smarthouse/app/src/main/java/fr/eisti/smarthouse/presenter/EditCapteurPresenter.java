package fr.eisti.smarthouse.presenter;

import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;
import fr.eisti.smarthouse.view.fragment.EditCapteurFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class EditCapteurPresenter {
    private EditCapteurFragment fragment;

    public EditCapteurPresenter(EditCapteurFragment fragment) {
        this.fragment = fragment;
    }

    public void save(String name, String type, boolean activ) {
        Capteur capteur = new Capteur(name, type, activ);
        FirebaseCapteurProvider.save(fragment.getActivity(), capteur);
    }

    public void findInfos(String capteurName) {
        FirebaseCapteurProvider.findOne(fragment.getActivity().getApplicationContext(), capteurName, (c) -> fragment.fillFrom(c));
    }

    public void activateCapteur(String capteurName, boolean activ) {
        FirebaseCapteurProvider.switchActiv(fragment.getActivity(), capteurName, activ);
    }
}
