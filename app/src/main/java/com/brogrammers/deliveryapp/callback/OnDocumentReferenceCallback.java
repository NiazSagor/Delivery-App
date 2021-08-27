package com.brogrammers.deliveryapp.callback;

import com.google.firebase.firestore.DocumentReference;

public interface OnDocumentReferenceCallback {
    void onRiderLocationDocument(DocumentReference documentReference);

    void onRiderLocationDocumentNotFound();
}
