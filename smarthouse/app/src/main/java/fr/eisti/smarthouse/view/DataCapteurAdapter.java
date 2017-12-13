package fr.eisti.smarthouse.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.eisti.smarthouse.R;

/**
 * Created by alex on 13/12/17.
 */

public class DataCapteurAdapter extends ArrayAdapter<Double> {
    private final static int resource = R.layout.fragment_data_capteurs_item;

    private final Context context;
    private List<Double> datas;

    public DataCapteurAdapter(@NonNull Context context, int ignored, @NonNull List<Double> datas) {
        super(context, resource, datas);
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(resource, null);

        TextView tvData = row.findViewById(R.id.tv_data_capteur);

        double data = datas.get(position);

        tvData.setText(String.valueOf(data));

        return row;
    }

    public void swapItems(List<Double> items) {
        this.datas.clear();
        this.datas.addAll(items);
        notifyDataSetChanged();
    }
}
