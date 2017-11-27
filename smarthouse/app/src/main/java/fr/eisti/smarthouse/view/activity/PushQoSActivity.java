package fr.eisti.smarthouse.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.LeftMenuFragment;
import fr.eisti.smarthouse.view.fragment.PushQosFragment;

public class PushQoSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_qos);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.apq_fragment, PushQosFragment.newInstance())
                .add(R.id.nav_view, LeftMenuFragment.newInstance())
                .commit();
    }
}
