package com.example.rakeshvasal.myapplication.ServiceCalls;

import android.util.Log;

import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.Interface.CentralCallbacks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCalls {

    private FirebaseDatabase firebaseDBInstance = FirebaseDatabase.getInstance();
    private DatabaseReference dBRootReference = firebaseDBInstance.getReference();
    private FirebaseStorage firebaseStorageInstance = FirebaseStorage.getInstance();
    private StorageReference firebaseStorageRootReference = firebaseStorageInstance.getReference();
    private DatabaseReference userDbReference = dBRootReference.child("users");


    public void getAllUsers(final CentralCallbacks centralCallbacks) {

        final List<User> mUserEntries = new ArrayList<>();
        try {
            userDbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        User user = eventsnapshot.getValue(User.class);
                        mUserEntries.add(user);
                    }
                    centralCallbacks.onSuccess(mUserEntries);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                    centralCallbacks.onFailure(databaseError);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            centralCallbacks.onFailure(e);
        }
    }

    public void getUserDetails(final String username, final CentralCallbacks centralCallbacks){
        try {
            final List<User> mUserEntries = new ArrayList<>();
            userDbReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        User user = eventsnapshot.getValue(User.class);
                        String user_name = user.getUser_name();
                        Log.d("user_name", "" + user_name);
                        if (user_name.contains(username)) {
                            mUserEntries.add(user);
                        }
                    }
                    centralCallbacks.onSuccess(mUserEntries);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                    centralCallbacks.onFailure(databaseError);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            centralCallbacks.onFailure(e);
        }
    }

    public interface OnServerCallsResponse<T> {
        void onSuccess(T response);

        void onFailure();
    }
}
