package fr.eisti.smarthouse.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.view.fragment.EditCapteurFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class EditCapteurActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_capteur);

        String capteurName = getIntent().getStringExtra(Capteur.NAME);

        getFragmentManager().beginTransaction()
                .add(R.id.aec_fragment_edit, EditCapteurFragment.newInstance(capteurName))
                .commit();
    }
}
