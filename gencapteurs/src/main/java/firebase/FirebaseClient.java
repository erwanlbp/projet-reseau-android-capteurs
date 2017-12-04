package firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import model.Capteur;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FirebaseClient {

    private static final String FIREBASE_URL= "https://smarthouse-31e81.firebaseio.com";

    //Latch to wait firebase response
    private final CountDownLatch latch = new CountDownLatch (1);
    private DatabaseReference db;

    public FirebaseClient() throws IOException, InterruptedException {

        initFirebaseClient();

/*        getAllCapteurs(list -> {
            for(Capteur c: list) {
                System.out.println(c);
            }
            latch.countDown();
        });*/
    }

    private void getAllCapteurs(GetAllCallback getAllCallback) throws InterruptedException {
        System.out.println("##### Get all capteurs beginning");

        db.child("capteurs")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Capteur> capteurs = new ArrayList<>();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            capteurs.add(Capteur.fromDataSnapshot(ds));
                        }

                        getAllCallback.retrieve(capteurs);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        if (this.latch.getCount() > 0){
            this.latch.await();
        }
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

    public void sentPutRequest(Capteur capteur) {
        db.child("capteurs").child(capteur.getName()).setValueAsync(capteur);
//        db.child("capteurs").child("data").child(capteur.getName()).child("value").push();
    }
}