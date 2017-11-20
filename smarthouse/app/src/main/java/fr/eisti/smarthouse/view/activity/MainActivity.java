package fr.eisti.smarthouse.view.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.eisti.smarthouse.R;
import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.provider.CapteurContractProvider;
import fr.eisti.smarthouse.provider.FirebaseCapteurProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Capteur capteur = new Capteur("Capteur de chaudasse", "Temperature", true);
                FirebaseCapteurProvider fcp = new FirebaseCapteurProvider();
                Uri uriInserted = fcp.insert(CapteurContractProvider.CONTENT_URI, capteur.asContentValues());
            }
        });
    }
}