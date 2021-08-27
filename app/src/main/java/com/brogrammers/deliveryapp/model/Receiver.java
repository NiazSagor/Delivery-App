package com.brogrammers.deliveryapp.model;

public class Receiver {

    private String additionalDirections, cashToCollect, receiverAddress, receiverName, receiverPhoneNumber;
    private boolean willPay;

    public Receiver() {
    }

    public Receiver(String additionalDirections, String cashToCollect, String receiverAddress, String receiverName, String receiverPhoneNumber, boolean willPay) {
        this.additionalDirections = additionalDirections;
        this.cashToCollect = cashToCollect;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.willPay = willPay;
    }

    public String getAdditionalDirections() {
        return additionalDirections;
    }

    public String getCashToCollect() {
        return cashToCollect;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public boolean isWillPay() {
        return willPay;
    }

    public void setWillPay(boolean willPay) {
        this.willPay = willPay;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
}
