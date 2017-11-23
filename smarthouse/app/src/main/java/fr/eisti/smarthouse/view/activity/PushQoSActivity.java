package fr.eisti.smarthouse.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.PushQosFragment;

public class PushQoSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_qos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.apq_fragment, PushQosFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(this, CapteursListActivity.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, CapteursListActivity.class));
        finish();
    }
}
