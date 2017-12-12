package fr.eisti.smarthouse.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.view.fragment.EditCapteurFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class EditCapteurActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_capteur);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
        	Toast.makeText(this, "Can't access this if not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        String capteurName = getIntent().getStringExtra(Capteur.NAME);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .add(R.id.aec_fragment_edit, EditCapteurFragment.newInstance(capteurName))
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
