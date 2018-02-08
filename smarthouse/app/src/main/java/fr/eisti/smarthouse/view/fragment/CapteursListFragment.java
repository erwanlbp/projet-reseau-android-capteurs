package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.presenter.CapteursListPresenter;
import fr.eisti.smarthouse.view.CapteursListAdapter;
import fr.eisti.smarthouse.view.activity.EditCapteurActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final int ACTIONS_REQUEST_CODE = 10;

    private CapteursListPresenter presenter;
    private CapteursListAdapter adapter;

    public static CapteursListFragment newInstance() {
        CapteursListFragment fragment = new CapteursListFragment();
        fragment.presenter = new CapteursListPresenter(fragment);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.fillCapteursList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capteurs_list, container, false);

        ListView capteursList = view.findViewById(R.id.capteursList);
        adapter = new CapteursListAdapter(getActivity(), R.layout.fragment_capteurs_list_item, new ArrayList<>());

        capteursList.setAdapter(adapter);
        capteursList.setOnItemClickListener(this);

        Button switchButton = view.findViewById(R.id.enableCapteurButton);
        switchButton.setOnClickListener(v -> launchVocalSearch());

        return view;
    }

    public void fillCapteursList(List<Capteur> capteurs) {
        adapter.swapItems(capteurs);
    }

    public void startEditActivity(String capteurName) {
        Intent intent = new Intent(getActivity(), EditCapteurActivity.class);

        intent.putExtra(Capteur.NAME, capteurName);

        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.itemClicked(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIONS_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String sentence = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);

            presenter.vocalActivateCapteur(sentence, adapter);
        }
    }

    private void launchVocalSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        startActivityForResult(intent, ACTIONS_REQUEST_CODE);
    }

    public void error(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
