package fr.eisti.smarthouse.view.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.model.NetworkConfig;
import fr.eisti.smarthouse.presenter.CapteursListPresenter;
import fr.eisti.smarthouse.provider.CapteurNetworkProvider;
import fr.eisti.smarthouse.view.CapteursListAdapter;
import fr.eisti.smarthouse.view.activity.EditCapteurActivity;
import fr.eisti.smarthouse.view.activity.SignInActivity;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListFragment extends ListFragment {

    private static final String TAG = "CapteursListFragment";

    private CapteursListPresenter presenter;
    private CapteursListAdapter adapter;

    public static CapteursListFragment newInstance() {
        CapteursListFragment fragment = new CapteursListFragment();
        fragment.presenter = new CapteursListPresenter(fragment);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), SignInActivity.class));
        }

        View view = inflater.inflate(R.layout.fragment_capteurs_list, container, false);

        this.setNetworkConfig();

        adapter = new CapteursListAdapter(getActivity(), R.layout.fragment_capteurs_list_item, new ArrayList<>());

        this.setListAdapter(adapter);

        return view;
    }

    private void setNetworkConfig() {
        FirebaseDatabase.getInstance().getReference()
                .child("config")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NetworkConfig networkConfig = dataSnapshot.getValue(NetworkConfig.class);
                        CapteurNetworkProvider.ipDestGenCapteurs = networkConfig.getIp_dest_gen_capteurs();
                        CapteurNetworkProvider.portDestGenCapteurs = networkConfig.getPort_dest_gen_capteurs();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fillCapteursList();
    }

    public void fillCapteursList(List<Capteur> capteurs) {
        adapter.swapItems(capteurs);
    }

    public void startEditActivity(String capteurName) {
        if (CapteurNetworkProvider.ipDestGenCapteurs != null && CapteurNetworkProvider.portDestGenCapteurs != null) {
            Intent intent = new Intent(getActivity(), EditCapteurActivity.class);

            intent.putExtra(Capteur.NAME, capteurName);

            startActivity(intent);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        presenter.itemClicked(v);
    }
}
