package com.brogrammers.deliveryapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.brogrammers.deliveryapp.callback.OnDocumentReferenceCallback;
import com.brogrammers.deliveryapp.callback.OnOrderTrackCollectionQueryCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ParcelOrderStatusActivityViewModel extends ViewModel {

    private static final String TAG = "ParcelOrderStatusActivi";

    public void getRiderLocationDocumentCallback(String documentId, OnDocumentReferenceCallback onDocumentReferenceCallback) {

        Query parcelCollection = FirebaseFirestore.getInstance().collection("PARCEL").whereEqualTo("documentId", documentId);
        parcelCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    queryDocumentSnapshots
                            .getDocuments().get(0).getReference()
                            .collection("rider location")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        onDocumentReferenceCallback.onRiderLocationDocument(
                                                queryDocumentSnapshots.getDocumentChanges().get(0).getDocument().getReference()
                                        );
                                    }

                                }
                            });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                onDocumentReferenceCallback.onRiderLocationDocumentNotFound();
            }
        });

    }

    public void getOrderTrackDocumentCallback(String documentId, OnOrderTrackCollectionQueryCallback onOrderTrackCollectionQueryCallback) {

        Query parcelCollection = FirebaseFirestore.getInstance().collection("PARCEL").whereEqualTo("documentId", documentId);

        parcelCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    onOrderTrackCollectionQueryCallback.onOrderTrackCollectionQuery(
                            queryDocumentSnapshots
                                    .getDocuments().get(0).getReference()
                                    .collection("order track")
                    );
                }

            }
        });

    }

}
