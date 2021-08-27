package com.brogrammers.deliveryapp.bottomsheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.callback.OnLocationBottomSheetInteraction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;


public class LocationSelectionBottomSheet implements View.OnClickListener {

    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private TextView pickUpLocation, destinationLocation;
    private MaterialButton confirmButton;

    public LocationSelectionBottomSheet(Context context, OnLocationBottomSheetInteraction onLocationBottomSheetInteraction) {
        this.onLocationBottomSheetInteraction = onLocationBottomSheetInteraction;
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.location_selection_layout, null);
        pickUpLocation = bottomSheetView.findViewById(R.id.pickup_location);
        destinationLocation = bottomSheetView.findViewById(R.id.dropoff_location);
        confirmButton = bottomSheetView.findViewById(R.id.confirm_button);
    }

    public void showBottomSheetDialog() {
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(true);
        pickUpLocation.setOnClickListener(this);
        destinationLocation.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    public void hideBottomSheetDialog() {
        bottomSheetDialog.dismiss();
    }

    public boolean isSheetVisible() {
        return bottomSheetDialog.isShowing();
    }

    public void setPickUpLocation(String address) {
        pickUpLocation.setText(address);
    }

    public void setDestinationLocation(String address) {
        destinationLocation.setText(address);
    }

    public String getDestinationLocation() {
        return destinationLocation.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pickup_location) {
            onLocationBottomSheetInteraction.onClickPickupAddress();
        } else if (v.getId() == R.id.dropoff_location) {
            onLocationBottomSheetInteraction.onClickDestinationAddress();
        } else {
            onLocationBottomSheetInteraction.onRouteConfirmed();
        }
    }

    private final OnLocationBottomSheetInteraction onLocationBottomSheetInteraction;
}
