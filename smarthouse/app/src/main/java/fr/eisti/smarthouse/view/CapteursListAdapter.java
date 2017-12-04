package fr.eisti.smarthouse.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class CapteursListAdapter extends ArrayAdapter<Capteur> {

    private final static int resource = R.layout.fragment_capteurs_list_item;

    private final Context context;
    private List<Capteur> capteurs;

    public CapteursListAdapter(@NonNull Context context, int ignored, @NonNull List<Capteur> capteurs) {
        super(context, resource, capteurs);
        this.context = context;
        this.capteurs = capteurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(resource, null);

        TextView tvName = row.findViewById(R.id.fcli_name_capteur);
        TextView tvType = row.findViewById(R.id.fcli_type_capteur);
        CheckBox chkActiv = row.findViewById(R.id.fcli_activ_capteur);

        Capteur capteur = capteurs.get(position);

        tvName.setText(capteur.getName());
        tvType.setText(capteur.getType());
        chkActiv.setChecked(capteur.isActivated());

        return row;
    }

    public void swapItems(List<Capteur> items) {
        this.capteurs.clear();
        this.capteurs.addAll(items);
        notifyDataSetChanged();
    }
}
