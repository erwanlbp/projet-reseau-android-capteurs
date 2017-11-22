package fr.eisti.smarthouse.view.activity;

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
import android.view.MenuItem;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Header;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_capteur);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getFragmentManager().beginTransaction()
                .add(R.id.acl_fragment_list, CapteursListFragment.newInstance())
                .commit();

        Header header = new Header(this);
        header.initHeader();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Snackbar.make(drawerLayout, item.getTitle(), Snackbar.LENGTH_SHORT).show();
        Log.i("#####", "onNavigationItemSelected");
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("#####", "onPostCreate");
        actionBarDrawerToggle.syncState();
    }
}
