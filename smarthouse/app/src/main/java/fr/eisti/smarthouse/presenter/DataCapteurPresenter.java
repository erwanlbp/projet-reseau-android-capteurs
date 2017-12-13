package fr.eisti.smarthouse.presenter;

import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;
import fr.eisti.smarthouse.view.fragment.DataCapteurFragment;

/**
 * Created by alex on 13/12/17.
 */

public class DataCapteurPresenter {

    DataCapteurFragment fragment;

    public DataCapteurPresenter(DataCapteurFragment fragment) {
        this.fragment = fragment;
    }

    public void fillDataList(String capteurName) {
        FirebaseCapteurProvider.findAllDatas(fragment.getActivity().getApplicationContext(), capteurName, (d) -> fragment.addToDataList(d));
    }
}
