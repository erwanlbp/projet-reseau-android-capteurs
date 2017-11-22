package fr.eisti.smarthouse.model;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.eisti.smarthouse.R;

/**
 * Created by alex on 22/11/17.
 */

public class Header {

    private Activity activity;

    public Header(Activity activity) {
        this.activity = activity;
    }

    public void initHeader() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userName = null;
        String email = null;

        if (firebaseUser != null) {
            userName = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
        }

        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView textViewName = headerView.findViewById(R.id.menu_name_tv);
        TextView textViewEmail = headerView.findViewById(R.id.menu_email_tv);

        textViewName.setText(userName);
        textViewEmail.setText(email);
    }
}
