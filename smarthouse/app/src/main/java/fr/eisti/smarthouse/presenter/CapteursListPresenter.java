package fr.eisti.smarthouse.presenter;

import android.view.View;
import android.widget.TextView;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListPresenter {

    private CapteursListFragment fragment;

    public CapteursListPresenter(CapteursListFragment fragment) {
        this.fragment = fragment;
    }

    public void fillCapteursList() {
        FirebaseCapteurProvider.findAll(fragment.getActivity().getApplicationContext(), (l) -> fragment.fillCapteursList(l));
    }

    public void itemClicked(View view) {
        fragment.startEditActivity(((TextView)view.findViewById(R.id.fcli_name_capteur)).getText().toString());
    }
}
