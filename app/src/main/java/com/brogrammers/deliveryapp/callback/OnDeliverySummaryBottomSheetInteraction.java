package com.brogrammers.deliveryapp.callback;

public interface OnDeliverySummaryBottomSheetInteraction {
    void onPayerChanged(String payer);
    void onPaymentMethodChanged(String paymentMethod);
    void onPickupRequested();
}
