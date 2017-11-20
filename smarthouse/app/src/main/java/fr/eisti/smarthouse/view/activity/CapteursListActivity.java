package fr.eisti.smarthouse.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_capteur);

        getFragmentManager().beginTransaction()
                .add(R.id.acl_fragment_list, CapteursListFragment.newInstance())
                .commit();
    }
}
