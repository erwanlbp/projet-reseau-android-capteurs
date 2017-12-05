package fr.eisti.smarthouse.provider;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fr.eisti.smarthouse.model.NetworkConfig;

public class FirebaseConfigProvider {
    private static final String TAG = "FirebaseConfigProvider";

    private static final String NODE_CONFIG = "config";

    public static void setNetworkConfig(Context context) {
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
                        Toast.makeText(context, "Can't retrieve network config from firebase : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
