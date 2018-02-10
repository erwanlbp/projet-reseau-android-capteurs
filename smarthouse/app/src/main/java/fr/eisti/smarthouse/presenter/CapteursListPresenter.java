package fr.eisti.smarthouse.presenter;

import android.util.Log;
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
        FirebaseCapteurProvider.findAll((m) -> fragment.error(m), (l) -> fragment.fillCapteursList(l));
    }

    public void itemClicked(View view) {
        fragment.startEditActivity(view.findViewById(R.id.fcli_name_capteur).toString());
    }

    public void activateCapteur(String capteurName, boolean activ) {
        FirebaseCapteurProvider.switchActiv((m) -> fragment.error(m), capteurName, activ);
    }

    public void vocalActivateCapteur(String sentence, CapteursListAdapter adapter) {
        Toast errorToast = Toast.makeText(fragment.getActivity().getApplicationContext(), "Votre phrase doit contenir : active/activer/désactive/désactiver et numéro index", Toast.LENGTH_LONG);

        if (isValid(sentence)) {
            Map<String, String> map = getWords(sentence);

            String index = map.get("index");
            String value = map.get("activated");

            if (index != null) {
                Boolean activated;
                switch (value) {
                    case "activer":
                    case "active":
                        activated = true;
                        break;
                    case "désactiver":
                    case "désactive":
                        activated = false;
                        break;
                    default:
                        activated = false;
                }
                try {
                    Capteur capteur = adapter.getItem(Integer.valueOf(index) - 1);
                    activateCapteur(capteur.getName(), activated);
                } catch (Exception e) {
                    Toast.makeText(fragment.getActivity(), (Integer.valueOf(index) - 1) + " ne renvoi pas vers un capteur", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        Log.i("#####", sentence);


        errorToast.show();
    }

    private boolean isValid(String sentence) {
        //Espaces faits exprès
        return ((sentence.contains("active ") ||
                sentence.contains("désactive ") ||
                sentence.contains("activer ") ||
                sentence.contains("désactiver ")) &&
                sentence.contains("numéro "));
    }

    private Map<String, String> getWords(String sentence) {
        String[] sentenceSplitted = sentence.split(" ");
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < sentenceSplitted.length; i++) {
            switch (sentenceSplitted[i]) {
                case "activer":
                case "active":
                    map.put("activated", sentenceSplitted[i]);
                    break;
                case "désactiver":
                case "désactive":
                    map.put("activated", sentenceSplitted[i]);
                    break;
                case "numéro":
                    if (i < sentenceSplitted.length - 1) map.put("index", sentenceSplitted[i + 1]);
                    break;
                default:
            }
        }

        return map;
    }

    public void addNew() {
        fragment.startEditActivity(null);
    }
}
