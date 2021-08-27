package com.brogrammers.deliveryapp.callback;

import com.brogrammers.deliveryapp.model.LocationStatus;

public interface OnRiderLocationUpdateCallback {
    void onRiderLocationUpdate(LocationStatus locationStatus);
}
