package fr.eisti.smarthouse.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.SignInFragment;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getFragmentManager().beginTransaction()
                .add(R.id.fsi_fragment_sign_in, SignInFragment.newInstance())
                .commit();
    }
}
