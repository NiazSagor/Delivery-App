package com.brogrammers.deliveryapp;

import android.util.Log;

import com.brogrammers.deliveryapp.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserHelper {

    private static final String TAG = "UserHelper";

    public static User user;

    public static UserHelper instance;

    public static UserHelper getInstance() {
        Log.d(TAG, "getInstance: ");
        getCurrentLoggedInUser(FirebaseAuth.getInstance());
        return instance;
    }

    private static void getCurrentLoggedInUser(FirebaseAuth firebaseAuth) {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            if (uid != null) {

                FirebaseFirestore.getInstance()
                        .collection("USERS")
                        .document(uid)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    user = documentSnapshot.toObject(User.class);
                                    Log.d(TAG, "onSuccess: " + user.getEmail());

                                }
                            }
                        });

            }
        } else {
            Log.d(TAG, "getCurrentLoggedInUser: user is null");
        }

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserHelper.user = user;
    }
}
