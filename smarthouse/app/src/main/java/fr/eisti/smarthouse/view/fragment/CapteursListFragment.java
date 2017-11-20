package fr.eisti.smarthouse.view.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.presenter.CapteursListPresenter;
import fr.eisti.smarthouse.view.CapteursListAdapter;
import fr.eisti.smarthouse.view.activity.EditCapteurActivity;

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
        View view = inflater.inflate(R.layout.fragment_capteurs_list, container, false);

        adapter = new CapteursListAdapter(getActivity(), R.layout.fragment_capteurs_list_item, new ArrayList<>());
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        presenter.itemClicked(v);
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
        Intent intent = new Intent(getActivity(), EditCapteurActivity.class);

        if (capteurName != null) {
            intent.putExtra(Capteur.NAME, capteurName);
        }

        startActivity(intent);
    }
}
