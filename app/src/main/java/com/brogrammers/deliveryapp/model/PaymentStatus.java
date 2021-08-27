package com.brogrammers.deliveryapp.model;

public class PaymentStatus {

    private String cashToCollect, paidBy, paymentMessage, paymentMethod;
    private boolean isPaid = false;


    public PaymentStatus() {
    }

    public PaymentStatus(boolean isPaid, String cashToCollect, String paidBy, String paymentMessage, String paymentMethod) {
        this.isPaid = isPaid;
        this.cashToCollect = cashToCollect;
        this.paidBy = paidBy;
        this.paymentMessage = paymentMessage;
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setCashToCollect(String cashToCollect) {
        this.cashToCollect = cashToCollect;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
