package fr.eisti.smarthouse.presenter;

import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.view.fragment.LeftMenuFragment;

/**
 * Created by alex on 22/11/17.
 */

public class LeftMenuPresenter {

    private LeftMenuFragment fragment;

    public LeftMenuPresenter(LeftMenuFragment fragment) {
        this.fragment = fragment;
    }

}
