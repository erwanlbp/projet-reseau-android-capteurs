package fr.eisti.smarthouse.provider;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.eisti.smarthouse.model.Capteur;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class FirebaseCapteurProvider {
    private static final String TAG = "FirebaseCapteurProvider";

    private static final String NODE_CAPTEURS = "capteurs";
    private static final String NODE_DATAS = "data";

    public static void findAll(final ErrorCallback ec, final FirebaseFindAllCallback ffac) {
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
                        ec.error(databaseError.getMessage());
                    }
                });
    }

    public static void findOne(final ErrorCallback ec, String capteurName, final FirebaseFindOneCallback ffoc) {
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
                        ec.error(databaseError.getMessage());
                    }
                });
    }

    public static void switchActiv(final ErrorCallback ec, String capteurName, boolean activ) {
        if (capteurName == null) {
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CAPTEURS)
                .child(capteurName)
                .updateChildren(Collections.singletonMap(Capteur.ACTIV, activ))
                .addOnFailureListener(e -> ec.error("switch failure for " + capteurName));

    }

    public static void findAllDatas(final ErrorCallback ec, final String capteurName, final FirebaseFindAllDataCallback ffac) {
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
                        ec.error("Child changed not implemented");
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        ec.error("Child removed not implemented");
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        ec.error("Child moved not implemented");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        ec.error(databaseError.getMessage());
                    }
                });
    }

    @FunctionalInterface
    public interface FirebaseFindAllDataCallback {
        void addToList(Double datas);
    }

    @FunctionalInterface
    public interface ErrorCallback {
        void error(String msg);
    }

    @FunctionalInterface
    public interface FirebaseFindAllCallback {
        void populate(List<Capteur> capteurs);
    }

    @FunctionalInterface
    public interface FirebaseFindOneCallback {
        void populate(Capteur capteur);
    }

}
