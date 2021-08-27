package com.brogrammers.deliveryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;


public class ParcelDeliveryOrder implements Parcelable{

    private String additionalDirections,
            destinationAddress,
            documentId,
            orderCategory,
            orderDistance,
            orderId,
            orderStatus,
            pickupAddress,
            receiverName,
            receiverNumber,
            riderPhoneNumber,
            senderName,
            senderNumber,
            timeStamp,
            totalCharge;


    public ParcelDeliveryOrder() {
    }

    public ParcelDeliveryOrder(String additionalDirections, String destinationAddress, String documentId, String orderDistance, String orderCategory, String orderId, String orderStatus, String pickupAddress, String receiverName, String receiverNumber, String riderPhoneNumber, String senderName, String senderNumber, String timeStamp, String totalCharge) {
        this.additionalDirections = additionalDirections;
        this.destinationAddress = destinationAddress;
        this.documentId = documentId;
        this.orderDistance = orderDistance;
        this.orderCategory = orderCategory;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.pickupAddress = pickupAddress;
        this.receiverName = receiverName;
        this.receiverNumber = receiverNumber;
        this.riderPhoneNumber = riderPhoneNumber;
        this.senderName = senderName;
        this.senderNumber = senderNumber;
        this.timeStamp = timeStamp;
        this.totalCharge = totalCharge;
    }


    protected ParcelDeliveryOrder(Parcel in) {
        additionalDirections = in.readString();
        destinationAddress = in.readString();
        documentId = in.readString();
        orderCategory = in.readString();
        orderDistance = in.readString();
        orderId = in.readString();
        orderStatus = in.readString();
        pickupAddress = in.readString();
        receiverName = in.readString();
        receiverNumber = in.readString();
        riderPhoneNumber = in.readString();
        senderName = in.readString();
        senderNumber = in.readString();
        timeStamp = in.readString();
        totalCharge = in.readString();
    }

    public static final Creator<ParcelDeliveryOrder> CREATOR = new Creator<ParcelDeliveryOrder>() {
        @Override
        public ParcelDeliveryOrder createFromParcel(Parcel in) {
            return new ParcelDeliveryOrder(in);
        }

        @Override
        public ParcelDeliveryOrder[] newArray(int size) {
            return new ParcelDeliveryOrder[size];
        }
    };

    public String getAdditionalDirections() {
        return additionalDirections;
    }

    public void setAdditionalDirections(String additionalDirections) {
        this.additionalDirections = additionalDirections;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(String orderDistance) {
        this.orderDistance = orderDistance;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public String getRiderPhoneNumber() {
        return riderPhoneNumber;
    }

    public void setRiderPhoneNumber(String riderPhoneNumber) {
        this.riderPhoneNumber = riderPhoneNumber;
    }

    @NotNull
    @Override
    public String toString() {
        return "ParcelDeliveryOrder{" +
                "additionalDirections='" + additionalDirections + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", documentId='" + documentId + '\'' +
                ", orderCategory='" + orderCategory + '\'' +
                ", orderDistance='" + orderDistance + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", pickupAddress='" + pickupAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverNumber='" + receiverNumber + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderNumber='" + senderNumber + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", totalCharge='" + totalCharge + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(additionalDirections);
        parcel.writeString(destinationAddress);
        parcel.writeString(documentId);
        parcel.writeString(orderCategory);
        parcel.writeString(orderDistance);
        parcel.writeString(orderId);
        parcel.writeString(orderStatus);
        parcel.writeString(pickupAddress);
        parcel.writeString(receiverName);
        parcel.writeString(receiverNumber);
        parcel.writeString(riderPhoneNumber);
        parcel.writeString(senderName);
        parcel.writeString(senderNumber);
        parcel.writeString(timeStamp);
        parcel.writeString(totalCharge);

    }
}
