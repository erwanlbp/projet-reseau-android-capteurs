package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import model.Capteur;

import java.io.IOException;
import java.io.InputStream;

public class FirebaseClient {

    private static final String FIREBASE_URL = "https://smarthouse-31e81.firebaseio.com";
    private DatabaseReference db;

    public FirebaseClient() throws IOException, InterruptedException {
        initFirebaseClient();
    }

    private void initFirebaseClient() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("smarthouse-credentials.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl(FIREBASE_URL)
                .build();

        FirebaseApp.initializeApp(options);

        db = FirebaseDatabase.getInstance().getReference();
    }

    public void sendPutRequest(Capteur capteur) {
        db.child("capteurs").child(capteur.getName()).setValueAsync(capteur);

        DatabaseReference dr = db.child("data").child(capteur.getName()).push();
        dr.setValueAsync(capteur.getData());
    }

    public void sendPortIn(int portIn) {
        db.child("config").child("portDestGenCapteurs").setValueAsync(portIn);
    }

    public void sendIpDest(String ipDest) {
        db.child("config").child("ipDestGenCapteurs").setValueAsync(ipDest);
    }
}
