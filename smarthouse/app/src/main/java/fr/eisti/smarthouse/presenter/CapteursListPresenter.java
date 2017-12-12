package fr.eisti.smarthouse.presenter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;
import fr.eisti.smarthouse.view.CapteursListAdapter;
import fr.eisti.smarthouse.view.fragment.CapteursListFragment;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListPresenter {

    private CapteursListFragment fragment;

    public CapteursListPresenter(CapteursListFragment fragment) {
        this.fragment = fragment;
    }

    public void fillCapteursList() {
        FirebaseCapteurProvider.findAll(fragment.getActivity().getApplicationContext(), (l) -> fragment.fillCapteursList(l));
    }

    public void itemClicked(View view) {
        fragment.startEditActivity(((TextView) view.findViewById(R.id.fcli_name_capteur)).getText().toString());
    }

    public void activateCapteur(String capteurName, boolean activ) {
        FirebaseCapteurProvider.switchActiv(fragment.getActivity(), capteurName, activ);
    }

    public void vocalActivateCapteur(String sentence, CapteursListAdapter adapter) {
        if(sentence.contains("capteur") && sentence.contains("numéro")) {

            Map<String, String> map = getWords(sentence);
            String index = map.get("index");
            String value = map.get("activated");

            Boolean activated;
            switch (value) {
                case "active":
                    activated = true;
                    break;
                case "désactive":
                    activated = false;
                    break;
                default:
                    activated = false;
            }

            Capteur capteur = adapter.getItem(Integer.valueOf(index) - 1);
            activateCapteur(capteur.getName(), activated);
        } else {
            Toast.makeText(fragment.getActivity().getApplicationContext(), "Dire : active/désactive le capteur numéro 1/2...", Toast.LENGTH_LONG).show();
        }
    }

    private Map<String, String> getWords(String sentence) {
        String[] sentenceSplitted = sentence.split(" ");
        Map<String, String> map = new HashMap<>();
        map.put("activated", sentenceSplitted[0]);
        map.put("index", sentenceSplitted[sentenceSplitted.length-1]);

        return map;
    }

    public void addNew() {
        fragment.startEditActivity(null);
    }
}
