package com.brogrammers.deliveryapp;

import java.io.Serializable;

public class Order implements Serializable {
    private String userUid, userName="", userMobile="", orderAddress="", instructions="";
    private String orderDetails,total="0",totalDeliveryFee="0",encodedCommission,totalCommission="0",totalPayment="0",discountAmount="0",discountCode,transactionCharge="0";
    private String shopName="", shopLogo="", sellerUid="";
    private String orderId="", documentId,monthYear="",year="";
    private String acceptedTime="", paymentTime="",paymentMethod="",processingTime="", deliveryTime="",paidTime="",paidMethod="",returnByUserTime="",returnBySellerTime="",refundedTime="",grandCancelTime="";
    private boolean accepted, payment, processed, returnBySeller, delivered,returnByUser, refunded, grandCancel, paid;
    private long createdTime;
    public Order() {
    }

    public Order(String userUid, String userName, String userMobile, String orderAddress, String instructions, String orderDetails, String total, String totalDeliveryFee, String encodedCommission, String totalCommission, String totalPayment, String discountAmount, String discountCode, String transactionCharge, String shopName, String shopLogo, String sellerUid, String orderId, String documentId, String monthYear, String year, String acceptedTime, String paymentTime, String paymentMethod, String processingTime, String deliveryTime, String paidTime, String paidMethod, String returnByUserTime, String returnBySellerTime, String refundedTime, String grandCancelTime, boolean accepted, boolean payment, boolean processed, boolean returnBySeller, boolean delivered, boolean returnByUser, boolean refunded, boolean grandCancel, boolean paid, long createdTime) {
        this.userUid = userUid;
        this.userName = userName;
        this.userMobile = userMobile;
        this.orderAddress = orderAddress;
        this.instructions = instructions;
        this.orderDetails = orderDetails;
        this.total = total;
        this.totalDeliveryFee = totalDeliveryFee;
        this.encodedCommission = encodedCommission;
        this.totalCommission = totalCommission;
        this.totalPayment = totalPayment;
        this.discountAmount = discountAmount;
        this.discountCode = discountCode;
        this.transactionCharge = transactionCharge;
        this.shopName = shopName;
        this.shopLogo = shopLogo;
        this.sellerUid = sellerUid;
        this.orderId = orderId;
        this.documentId = documentId;
        this.monthYear = monthYear;
        this.year = year;
        this.acceptedTime = acceptedTime;
        this.paymentTime = paymentTime;
        this.paymentMethod = paymentMethod;
        this.processingTime = processingTime;
        this.deliveryTime = deliveryTime;
        this.paidTime = paidTime;
        this.paidMethod = paidMethod;
        this.returnByUserTime = returnByUserTime;
        this.returnBySellerTime = returnBySellerTime;
        this.refundedTime = refundedTime;
        this.grandCancelTime = grandCancelTime;
        this.accepted = accepted;
        this.payment = payment;
        this.processed = processed;
        this.returnBySeller = returnBySeller;
        this.delivered = delivered;
        this.returnByUser = returnByUser;
        this.refunded = refunded;
        this.grandCancel = grandCancel;
        this.paid = paid;
        this.createdTime = createdTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalDeliveryFee() {
        return totalDeliveryFee;
    }

    public void setTotalDeliveryFee(String totalDeliveryFee) {
        this.totalDeliveryFee = totalDeliveryFee;
    }

    public String getEncodedCommission() {
        return encodedCommission;
    }

    public void setEncodedCommission(String encodedCommission) {
        this.encodedCommission = encodedCommission;
    }

    public String getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(String totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getTransactionCharge() {
        return transactionCharge;
    }

    public void setTransactionCharge(String transactionCharge) {
        this.transactionCharge = transactionCharge;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAcceptedTime() {
        return acceptedTime;
    }

    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public String getPaidMethod() {
        return paidMethod;
    }

    public void setPaidMethod(String paidMethod) {
        this.paidMethod = paidMethod;
    }

    public String getReturnByUserTime() {
        return returnByUserTime;
    }

    public void setReturnByUserTime(String returnByUserTime) {
        this.returnByUserTime = returnByUserTime;
    }

    public String getReturnBySellerTime() {
        return returnBySellerTime;
    }

    public void setReturnBySellerTime(String returnBySellerTime) {
        this.returnBySellerTime = returnBySellerTime;
    }

    public String getRefundedTime() {
        return refundedTime;
    }

    public void setRefundedTime(String refundedTime) {
        this.refundedTime = refundedTime;
    }

    public String getGrandCancelTime() {
        return grandCancelTime;
    }

    public void setGrandCancelTime(String grandCancelTime) {
        this.grandCancelTime = grandCancelTime;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isReturnBySeller() {
        return returnBySeller;
    }

    public void setReturnBySeller(boolean returnBySeller) {
        this.returnBySeller = returnBySeller;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isReturnByUser() {
        return returnByUser;
    }

    public void setReturnByUser(boolean returnByUser) {
        this.returnByUser = returnByUser;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public boolean isGrandCancel() {
        return grandCancel;
    }

    public void setGrandCancel(boolean grandCancel) {
        this.grandCancel = grandCancel;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
}
