package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.presenter.DataCapteurPresenter;
import fr.eisti.smarthouse.view.DataCapteurAdapter;

/**
 * Created by alex on 13/12/17.
 */

public class DataCapteurFragment extends Fragment {

    private String capteurName;

    private DataCapteurPresenter dataCapteurPresenter;
    private DataCapteurAdapter adapter;

    public static DataCapteurFragment newInstance(String capteurName) {
        DataCapteurFragment fragment = new DataCapteurFragment();
        fragment.dataCapteurPresenter = new DataCapteurPresenter(fragment);
        fragment.capteurName = capteurName;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        dataCapteurPresenter.fillDataList(capteurName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_capteurs, container, false);

        ListView capteursList = view.findViewById(R.id.dataCapteurs);

        adapter = new DataCapteurAdapter(getActivity(), R.layout.fragment_data_capteurs_item, new ArrayList<>());

        capteursList.setAdapter(adapter);

        return view;
    }

    public void addToDataList(Double data) {
        adapter.addItem(data);
    }

    public void error(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
