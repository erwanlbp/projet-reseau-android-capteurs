package fr.eisti.smarthouse.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;
import fr.eisti.smarthouse.view.fragment.LeftMenuFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_capteur);

        getFragmentManager().beginTransaction()
                .add(R.id.acl_fragment_list, CapteursListFragment.newInstance())
                .add(R.id.nav_view, LeftMenuFragment.newInstance())
                .commit();
    }
}
