package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.presenter.EditCapteurPresenter;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class EditCapteurFragment extends Fragment {

    private static final String TAG = "EditCapteurFragment";

    private EditCapteurPresenter presenter;

    private String capteurName;

    private TextView tvName;
    private TextView tvType;
    private Switch switchActiv;

    public static EditCapteurFragment newInstance(String capteurName) {
        EditCapteurFragment fragment = new EditCapteurFragment();
        fragment.setPresenter(new EditCapteurPresenter(fragment));
        fragment.capteurName = capteurName;
        return fragment;
    }

    public void setPresenter(EditCapteurPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_capteur, container, false);

        tvName = view.findViewById(R.id.fec_name_capteur);
        tvType = view.findViewById(R.id.fec_type_capteur);
        switchActiv = view.findViewById(R.id.fec_activ_capteur);

        if (capteurName == null) {
            Toast.makeText(getActivity(), "Didn't receive a capteur name", Toast.LENGTH_SHORT).show();
        } else {
            presenter.findInfos(capteurName);
            switchActiv.setOnCheckedChangeListener((compoundButton, b) -> presenter.activateCapteur(capteurName, b));
        }

        return view;
    }

    public void fillFrom(Capteur capteur) {
        tvName.setText(capteur.getName());
        tvType.setText(capteur.getType());
        switchActiv.setChecked(capteur.isActivated());
    }

    public void error(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
