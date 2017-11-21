package fr.eisti.smarthouse.provider;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    static {
        FirebaseAuth.getInstance().signInAnonymously();
    }

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

    public static void save(final Context context, final Capteur capteur) {
        if (capteur == null) {
            return;
        }

        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CAPTEURS)
                .child(capteur.getName())
                .setValue(capteur)
                .addOnCompleteListener(task -> Toast.makeText(context, "saved " + capteur.getName() + ": " + task.isSuccessful(), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "saved failure for " + capteur.getName(), Toast.LENGTH_SHORT).show());
    }

    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
