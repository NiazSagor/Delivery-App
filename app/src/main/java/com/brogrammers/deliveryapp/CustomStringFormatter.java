package com.brogrammers.deliveryapp;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomStringFormatter {
    public static StringBuilder encodeSpecifications(List<Specification> specifications){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<specifications.size(); i++) {
            stringBuilder.append(specifications.get(i).getKey())
                    .append("$")
                    .append(specifications.get(i).getValue());
            if (i == specifications.size()-1) break;
            stringBuilder.append("#");
        }
        return stringBuilder;
    }

    public static List<Specification> decodeSpecifications(String encodedSpecifications){
        List<Specification> specifications = new ArrayList<>();
        String[] splitedItems = encodedSpecifications.split("#");
        for (String item : splitedItems) {
            String[] pair = item.split("[$]");
            if (pair.length == 2) specifications.add(new Specification(pair[0], pair[1]));
        }
        return specifications;
    }

    public static StringBuilder encodeVariations(List<Variation> variations){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<variations.size(); i++) {
            stringBuilder.append(variations.get(i).getSize())
                    .append("$")
                    .append(variations.get(i).getColor())
                    .append("$")
                    .append(variations.get(i).getQuantity());
            if (i == variations.size()-1) break;
            stringBuilder.append("#");
        }
        return stringBuilder;
    }

    public static List<Variation> decodeVariations(String encodedVariations){
        List<Variation> variations = new ArrayList<>();
        String[] splitedItems = encodedVariations.split("#");
        for (String item : splitedItems) {
            String[] pair = item.split("[$]");
            if (pair.length == 3) variations.add(new Variation(pair[0], pair[1],pair[2]));
        }
        return variations;
    }

    public static DeliveryPriceInfo decodeDeliveryPriceAndInfo(String encodedDeliveryInfo){
        DeliveryPriceInfo deliveryPriceInfo = new DeliveryPriceInfo();
        String[] decodedItems = encodedDeliveryInfo.split("#");
        if (decodedItems.length==4){
            try{
                deliveryPriceInfo.setDeliveryDetails(decodedItems[0]);
                deliveryPriceInfo.setBaseDeliveryPrice(Double.parseDouble(decodedItems[1]));
                deliveryPriceInfo.setPerIncrementNumber(Integer.parseInt(decodedItems[2]));
                deliveryPriceInfo.setPerIncrementPrice(Double.parseDouble(decodedItems[3]));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else return null;

        return deliveryPriceInfo;
    }

    /**Format milliseconds to Date and Time*/
    public static String formatMillisecondsIntoDate(long time){
        if (time<=0) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy",calendar).toString();
    }

    public static String formatMillisecondsIntoDateAndTime(long time){
        if (time<=0) return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy hh:mm aa",calendar).toString();
    }

    public static List<OrderTrackingItem> formatOrderTrackingList(Order order) {
        List<OrderTrackingItem> items = new ArrayList<>();

        items.add(new OrderTrackingItem("Order Placed","Order is placed to review.",formatMillisecondsIntoDateAndTime(order.getCreatedTime()),true));

        if (order.isPayment()){
            items.add(new OrderTrackingItem("Online Payment",""+order.getPaymentMethod(),order.getPaymentTime(),true));
        }

        if (order.isAccepted()){
            items.add(new OrderTrackingItem("Order Accepted","Order is accepted to process and take further action.",order.getAcceptedTime(),true));
        }


        if (order.isAccepted() && order.isProcessed()){
            items.add(new OrderTrackingItem("Order Shifted","Order is shifted to deliver and take further action.",order.getProcessingTime(),true));
        }

        if (order.isProcessed() && order.isReturnBySeller()){
            items.add(new OrderTrackingItem("Order Returned By Seller","Order is return without deliver and take further action.",order.getReturnBySellerTime(),true));
        }

        if (order.isAccepted() && order.isProcessed() && order.isDelivered()){
            items.add(new OrderTrackingItem("Order Delivered","Order is delivered to you and we are happy to have you.",order.getDeliveryTime(),true));
        }

        if (order.isDelivered() && order.isReturnByUser()){
            items.add(new OrderTrackingItem("Order Returned By User","Order is return to you and take further action.",order.getReturnByUserTime(),true));
        }

        if (order.isGrandCancel()){
            items.add(new OrderTrackingItem("Order Cancelled","Order is cancelled for some issues. We hope we will see you soon.",order.getGrandCancelTime(),true));
        }

        return items;
    }

    public static String calculateTotalAmount(String total, String totalDeliveryFee, String transactionCharge, String discount) {
        try{
            return String.valueOf(Double.parseDouble(total)+Double.parseDouble(totalDeliveryFee)+Double.parseDouble(transactionCharge)-Double.parseDouble(discount));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "0";
    }

    public static String calculateDueAmount(String total, String totalDeliveryFee, String transactionCharge, String discount, String totalPayment) {
        try{
            return String.valueOf(Double.parseDouble(total)+Double.parseDouble(totalDeliveryFee)+Double.parseDouble(transactionCharge)-Double.parseDouble(discount)-Double.parseDouble(totalPayment));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "0";
    }
}
