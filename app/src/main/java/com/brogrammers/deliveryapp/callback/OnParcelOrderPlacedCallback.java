package com.brogrammers.deliveryapp.callback;

import com.brogrammers.deliveryapp.model.ParcelDeliveryOrder;

public interface OnParcelOrderPlacedCallback {
    void onOrderPlaced(ParcelDeliveryOrder order);
}
