package fr.eisti.smarthouse.provider;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.eisti.smarthouse.model.Capteur;
import fr.eisti.smarthouse.provider.callback.FirebaseFindAllCallback;
import fr.eisti.smarthouse.provider.callback.FirebaseFindOneCallback;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class FirebaseCapteurProvider {
    private static final String TAG = "FirebaseCapteurProvider";

    private static final String NODE_CAPTEURS = "capteurs";
    private static final String NODE_DATAS = "data";

    public static void findAll(final Context context, final FirebaseFindAllCallback ffac) {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CAPTEURS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Capteur> capteurs = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            capteurs.add(Capteur.fromDataSnapshot(ds));
                        }

                        ffac.populate(capteurs);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void findOne(final Context context, String capteurName, final FirebaseFindOneCallback ffoc) {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CAPTEURS)
                .child(capteurName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Capteur capteur = Capteur.fromDataSnapshot(dataSnapshot);
                        ffoc.populate(capteur);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void switchActiv(Context context, String capteurName, boolean activ) {
        if (capteurName == null) {
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CAPTEURS)
                .child(capteurName)
                .updateChildren(Collections.singletonMap(Capteur.ACTIV, activ))
                .addOnFailureListener(e -> Toast.makeText(context, "switch failure for " + capteurName, Toast.LENGTH_SHORT).show());

    }

    public static void findAllDatas(final Context context, final String capteurName, final FirebaseFindAllDataCallback ffac) {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_DATAS)
                .child(capteurName)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ffac.addToList(dataSnapshot.getValue(Double.class));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Toast.makeText(context, "Child changed not implemented", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Toast.makeText(context, "Child removed not implemented", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        Toast.makeText(context, "Child moved not implemented", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @FunctionalInterface
    public interface FirebaseFindAllDataCallback {
        void addToList(Double datas);
    }
}
