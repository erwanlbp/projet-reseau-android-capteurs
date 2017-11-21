package fr.eisti.smarthouse.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.presenter.EditCapteurPresenter;
import fr.eisti.smarthouse.view.activity.CapteursListActivity;
import fr.eisti.smarthouse.view.activity.SignInActivity;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class EditCapteurFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditCapteurFragment";

    private EditCapteurPresenter presenter;

    private String capteurName;

    private EditText edtName;
    private EditText edtType;
    private CheckBox chkActiv;
    private Button btnEdit;

    public static EditCapteurFragment newInstance(String capteurName) {
        EditCapteurFragment fragment = new EditCapteurFragment();
        fragment.presenter = new EditCapteurPresenter(fragment);
        fragment.capteurName = capteurName;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(getActivity(), SignInActivity.class));
        }

        View view = inflater.inflate(R.layout.fragment_edit_capteur, container, false);

        edtName = view.findViewById(R.id.fec_name_capteur);
        edtType = view.findViewById(R.id.fec_type_capteur);
        chkActiv = view.findViewById(R.id.fec_activ_capteur);
        btnEdit = view.findViewById(R.id.fec_edit_capteur);

        edtName.setFilters(new InputFilter[] { setEditTextFilter() });

        btnEdit.setOnClickListener(this);

        if (capteurName != null)
            presenter.findInfos(capteurName);

        return view;
    }

    public InputFilter setEditTextFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                List<String> unauthorizedCharacters = new ArrayList<>(Arrays.asList(".", "$", "[", "]", "#", "/"));

                for (int i = start; i < end; i++) {
                    if (unauthorizedCharacters.contains(String.valueOf(source.charAt(i)))) {
                        Toast.makeText(getActivity(), "The name shouldn't contain any of these characters : '.' '$' '[' ']' '#' '/'", Toast.LENGTH_LONG).show();
                        return "";
                    }
                }
                return null;
            }
        };

        return filter;
    }

    public void fillFrom(Capteur capteur) {
        edtName.setText(capteur.getName());
        edtType.setText(capteur.getType());
        chkActiv.setChecked(capteur.isActiv());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnEdit.getId()) {
            presenter.save(edtName.getText().toString(), edtType.getText().toString(), chkActiv.isChecked());
        }
    }
}
