package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eisti.smarthouse.R;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListFragment extends Fragment {

    public static final String TAG = "CapteursListFragment";


    public static CapteursListFragment newInstance() {
        CapteursListFragment fragment = new CapteursListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capteurs_list, container, false);

        return view;
    }

}
