package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import controlers.dbinterface.StartStopCallback;
import model.Capteur;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseClient {

    private static final String FIREBASE_URL = "https://smarthouse-31e81.firebaseio.com";
    private DatabaseReference db;
    private static FirebaseClient instance;

    private FirebaseClient() throws IOException, InterruptedException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("smarthouse-credentials.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl(FIREBASE_URL)
                .build();

        FirebaseApp.initializeApp(options);
    }

    public static FirebaseClient getInstance() throws IOException, InterruptedException {
        if (instance == null)
            instance = new FirebaseClient();
        return instance;
    }

    public void sendCapteur(Capteur capteur) {
        db = FirebaseDatabase.getInstance().getReference();
        db.child("capteurs").child(capteur.getName()).setValueAsync(capteur);
    }

    public void sendData(Capteur capteur) {
        DatabaseReference dr = db.child("data").child(capteur.getName()).push();
        dr.setValueAsync(capteur.getData());
    }

    public void listenStartStop(StartStopCallback startStopCallback) {
        db = FirebaseDatabase.getInstance().getReference().child("capteurs");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Capteur capteur = Capteur.fromSnapshot(dataSnapshot);
                startStopCallback.switchCapteur(capteur);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
