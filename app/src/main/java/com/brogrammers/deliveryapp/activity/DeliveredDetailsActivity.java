package com.brogrammers.deliveryapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.brogrammers.deliveryapp.CustomStringFormatter;
import com.brogrammers.deliveryapp.databinding.ActivityDeliveredDetailsBinding;
import com.brogrammers.deliveryapp.model.ParcelDeliveryOrder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeliveredDetailsActivity extends AppCompatActivity {

    private ActivityDeliveredDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeliveredDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = new Intent();

        ParcelDeliveryOrder parcelDeliveryOrder = intent.getParcelableExtra("parcel");

        binding.totalDistance.setText(parcelDeliveryOrder.getTotalCharge());
        binding.totalCost.setText(parcelDeliveryOrder.getTotalCharge());
        binding.additionalDirection.setText(parcelDeliveryOrder.getAdditionalDirections());
        binding.receiverName.setText(parcelDeliveryOrder.getReceiverName());
        binding.receiverAddress.setText(parcelDeliveryOrder.getDestinationAddress());
        binding.senderName.setText(parcelDeliveryOrder.getSenderName());
        binding.senderAddress.setText(parcelDeliveryOrder.getPickupAddress());
        binding.orderPlacedAt.setText(CustomStringFormatter.formatMillisecondsIntoDateAndTime(Long.parseLong(parcelDeliveryOrder.getTimeStamp())));

        FirebaseFirestore.getInstance().collection("PARCEL")
                .document(parcelDeliveryOrder.getDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            documentSnapshot.getReference().collection("rider details")
                                    .document()
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {

                                                if (documentSnapshot.getData().containsKey("deliveryFinishedAt")) {
                                                    binding.orderDeliveredAt.setText(CustomStringFormatter.formatMillisecondsIntoDateAndTime(Long.parseLong(documentSnapshot.getData().get("deliveryFinishedAt").toString())));
                                                }

                                            }
                                        }
                                    });

                        }
                    }
                });
    }
}