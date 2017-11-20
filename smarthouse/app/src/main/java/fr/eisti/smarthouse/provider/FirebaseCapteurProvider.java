package fr.eisti.smarthouse.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.eisti.smarthouse.model.Capteur;

/**
 * Created by ErwanLBP on 20/11/17.
 */

public class FirebaseCapteurProvider extends ContentProvider {
    private static final String TAG = "FirebaseCapteurProvider";

    private static final int CAPTEURS = 1;
    private static final int CAPTEUR_NAME = 2;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CapteurContractProvider.AUTHORITY, CapteurContractProvider.BASE_PATH, CAPTEURS);
        uriMatcher.addURI(CapteurContractProvider.AUTHORITY, CapteurContractProvider.BASE_PATH + "/#", CAPTEUR_NAME);
    }

    public FirebaseCapteurProvider() {
        FirebaseAuth.getInstance().signInAnonymously();
    }

    @Override
    public boolean onCreate() {

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        DatabaseReference capteurRef = FirebaseDatabase.getInstance().getReference().child("capteurs");

        switch (uriMatcher.match(uri)) {
            case CAPTEURS:
                break;
            case CAPTEUR_NAME:
                capteurRef.child(uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if(s1 != null) {
            capteurRef.orderByChild(s1);
        }

        capteurRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CAPTEURS:
                return CapteurContractProvider.CONTENT_TYPE;
            case CAPTEUR_NAME:
                return CapteurContractProvider.CONTENT_CAPTEUR_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (contentValues == null) {
            return null;
        }

        final Capteur capteur = Capteur.fromContentValues(contentValues);

        switch (uriMatcher.match(uri)) {
            case CAPTEURS:
                FirebaseDatabase.getInstance().getReference().child("capteurs").child(capteur.getName()).setValue(capteur)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i(TAG, "inserted " + capteur.getName() + ": " + task.isSuccessful());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "insert failure for " + capteur.getName());
                            }
                        });
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
//        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CapteurContractProvider.BASE_PATH + "/" + capteur.getName());
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
