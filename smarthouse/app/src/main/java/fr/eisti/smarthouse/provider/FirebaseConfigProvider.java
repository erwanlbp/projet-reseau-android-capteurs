package fr.eisti.smarthouse.provider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.eisti.smarthouse.model.NetworkConfig;

public class FirebaseConfigProvider {
    private static final String TAG = "FirebaseConfigProvider";

    private static final String NODE_CONFIG = "config";

    public static void setNetworkConfig() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CONFIG)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NetworkConfig networkConfig = dataSnapshot.getValue(NetworkConfig.class);
                        NetworkConfig.setNetworkConfig(networkConfig);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
    }
}
