package fr.eisti.smarthouse.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.presenter.SignInPresenter;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;
import fr.eisti.smarthouse.view.fragment.LeftMenuFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListActivity extends AppCompatActivity {
    private SignInPresenter signInPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_capteur);

        signInPresenter = new SignInPresenter(this);

        getFragmentManager().beginTransaction()
                .add(R.id.acl_fragment_list, CapteursListFragment.newInstance())
                .add(R.id.nav_view, LeftMenuFragment.newInstance())
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            signInPresenter.signIn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SignInPresenter.RC_LOGGED_IN && signInPresenter != null) {
            signInPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}
