package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

    public void fillDataList(List<Double> datas) {
        adapter.swapItems(datas);
    }

}
