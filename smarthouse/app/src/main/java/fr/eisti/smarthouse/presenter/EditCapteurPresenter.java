package fr.eisti.smarthouse.presenter;

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

    public void findInfos(String capteurName) {
        FirebaseCapteurProvider.findOne((m)->fragment.error(m), capteurName, (c) -> fragment.fillFrom(c));
    }

    public void activateCapteur(String capteurName, boolean activ) {
        FirebaseCapteurProvider.switchActiv((m)->fragment.error(m), capteurName, activ);
    }
}
