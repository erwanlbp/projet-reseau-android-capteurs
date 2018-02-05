package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import controlers.dbinterface.StartStopCallback;
import model.Capteur;

import java.io.IOException;
import java.io.InputStream;

/**
  Class that communicates with Firebase.
 */
public final class FirebaseClient {

    /**
     * Firebase project URL
     */
    private static final String FIREBASE_URL = "https://smarthouse-31e81.firebaseio.com";

    /**
     * Database reference
     */
    private DatabaseReference db;

    /**
     * Instance of the class
     */
    private static FirebaseClient instance;

    /**
     * Constructor that gets Firebase credentials and init
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    private FirebaseClient() throws IOException, InterruptedException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("smarthouse-credentials.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl(FIREBASE_URL)
                .build();

        FirebaseApp.initializeApp(options);
    }

    /**
     * Get the class instance
     * @return FirebaseClient
     * @throws IOException gdsgds
     * @throws InterruptedException gdsgds
     */
    public static FirebaseClient getInstance() throws IOException, InterruptedException {
        if (instance == null)
            instance = new FirebaseClient();
        return instance;
    }

    /**
     * Add a capteur to "capteurs" node into Firebase
     * @param capteur Capteur to send
     */
    public void sendCapteur(final Capteur capteur) {
        db = FirebaseDatabase.getInstance().getReference();
        db.child("capteurs").child(capteur.getName()).setValueAsync(capteur);
    }

    /**
     * Insert data to the "data" node into Firebase
     * @param capteur Capteur for which we need sens the data
     */
    public void sendData(final Capteur capteur) {
        DatabaseReference dr = db.child("data").child(capteur.getName()).push();
        dr.setValueAsync(capteur.getData());
    }

    /**
     * Swich capteur attribute into Firebase when a start or stop command is received
     * @param startStopCallback callback
     */
    public void listenStartStop(final StartStopCallback startStopCallback) {
        db = FirebaseDatabase.getInstance().getReference().child("capteurs");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, final String s) {
            }

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, final String s) {
                Capteur capteur = Capteur.fromSnapshot(dataSnapshot);
                startStopCallback.switchCapteur(capteur);
            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(final DataSnapshot dataSnapshot, final String s) {
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
            }
        });
    }
}
