package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.presenter.LeftMenuPresenter;

public class LeftMenuFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private LeftMenuPresenter presenter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    public LeftMenuFragment() {
        this.presenter = new LeftMenuPresenter(this);
    }

    public static LeftMenuFragment newInstance() {
        return new LeftMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(appCompatActivity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        initHeader();

        return view;
    }

    public void initHeader() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userName = null;
        String email = null;

        if (firebaseUser != null) {
            userName = firebaseUser.getDisplayName();
            email = firebaseUser.getEmail();
        }

        View headerView = navigationView.getHeaderView(0);

        TextView textViewName = headerView.findViewById(R.id.menu_name_tv);
        TextView textViewEmail = headerView.findViewById(R.id.menu_email_tv);

        textViewName.setText(userName);
        textViewEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Snackbar.make(drawerLayout, item.getTitle(), Snackbar.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("#####", "onOptionsItemSelected");
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("#####", "onConfigurationChanged");
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            Log.i("#####", "onActivityCreated");
            actionBarDrawerToggle.syncState();
    }
}
