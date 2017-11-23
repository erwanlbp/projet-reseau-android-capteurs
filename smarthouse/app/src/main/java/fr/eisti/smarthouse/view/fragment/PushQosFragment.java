package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.presenter.PushQosPresenter;

/**
 * Created by alex on 23/11/17.
 */

public class PushQosFragment extends Fragment implements View.OnClickListener {

    public static final int PICK_FILE = 12;
    private PushQosPresenter presenter;
    private DrawerLayout drawerLayout;

    private TextView tv_name_qos;
    private Button btn_browse;
    private Button btn_push;

    public PushQosFragment() {
        this.presenter = new PushQosPresenter(this);
    }

    public static PushQosFragment newInstance() {
        return new PushQosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_push_qos, container, false);

        drawerLayout = getActivity().findViewById(R.id.apq_drawer_layout);

        tv_name_qos = view.findViewById(R.id.fpq_name_qos);
        btn_browse = view.findViewById(R.id.fpq_browse);
        btn_push = view.findViewById(R.id.fpq_push);

        tv_name_qos = view.findViewById(R.id.fpq_name_qos);
        btn_browse.setOnClickListener(this);
        btn_push.setOnClickListener(this);

        return view;
    }

    public void browse() {
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE) {
            Snackbar.make(drawerLayout, "Searching ...", Snackbar.LENGTH_SHORT).show();
            tv_name_qos.setText(data.getDataString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fpq_browse:
                browse();
                break;
            case R.id.fpq_push:
                Snackbar.make(drawerLayout, "Not implemented", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                Snackbar.make(drawerLayout, "Not implemented", Snackbar.LENGTH_SHORT).show();
        }
    }
}
