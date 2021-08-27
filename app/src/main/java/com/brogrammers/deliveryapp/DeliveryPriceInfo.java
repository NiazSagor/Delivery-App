package com.brogrammers.deliveryapp;

public class DeliveryPriceInfo {
    private String deliveryDetails;
    private double baseDeliveryPrice = -1;
    private int perIncrementNumber = -1;
    private double perIncrementPrice = -1;

    public DeliveryPriceInfo() {
    }

    /**Encoded By:#*/


    public DeliveryPriceInfo(String deliveryDetails, double baseDeliveryPrice, int perIncrementNumber, double perIncrementPrice) {
        this.deliveryDetails = deliveryDetails;
        this.baseDeliveryPrice = baseDeliveryPrice;
        this.perIncrementNumber = perIncrementNumber;
        this.perIncrementPrice = perIncrementPrice;
    }

    public String getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(String deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public double getBaseDeliveryPrice() {
        return baseDeliveryPrice;
    }

    public void setBaseDeliveryPrice(double baseDeliveryPrice) {
        this.baseDeliveryPrice = baseDeliveryPrice;
    }

    public int getPerIncrementNumber() {
        return perIncrementNumber;
    }

    public void setPerIncrementNumber(int perIncrementNumber) {
        this.perIncrementNumber = perIncrementNumber;
    }

    public double getPerIncrementPrice() {
        return perIncrementPrice;
    }

    public void setPerIncrementPrice(double perIncrementPrice) {
        this.perIncrementPrice = perIncrementPrice;
    }
}
