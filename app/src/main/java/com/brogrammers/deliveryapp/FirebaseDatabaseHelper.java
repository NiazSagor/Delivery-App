package com.brogrammers.deliveryapp;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseDatabaseHelper {
    private static FirebaseDatabaseHelper instance;
    private Context context;

    public FirebaseFirestore db;
    public StorageReference storage;
    public FirebaseAuth mAuth;

    private FirebaseDatabaseHelper(Context context){
        this.context = context;
    }

    public static FirebaseDatabaseHelper getInstance(Application application){
        if (instance==null) instance = new FirebaseDatabaseHelper(application);
        return instance;
    }

    public void init(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);

        storage = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseFirestore getDb(){
        return db;
    }
    public StorageReference getStorage(){
        return storage;
    }
    public FirebaseAuth getAuth(){
        return mAuth;
    }
}
