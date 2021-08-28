package com.brogrammers.deliveryapp.utility;

import android.location.Address;
import android.util.Log;

import com.brogrammers.deliveryapp.AppInitializer;
import com.brogrammers.deliveryapp.Constants;
import com.brogrammers.deliveryapp.Result;
import com.brogrammers.deliveryapp.UserHelper;
import com.brogrammers.deliveryapp.model.LocationStatus;
import com.brogrammers.deliveryapp.model.ParcelOrderTrack;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.util.List;

public class Utility {

    private static final String TAG = "Utility";

    public static double getDistance(Result.Distance d) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < d.getText().length(); i++) {
            if (Character.isWhitespace(d.getText().charAt(i))) {
                break;
            } else {
                s.append(d.getText().charAt(i));
            }
        }
        return (Double.parseDouble(s.toString()));
    }

    public static String getDocumentIdGeneratedByFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference parcelCollectionRef = db.collection("PARCEL");
        return parcelCollectionRef.document().getId();
    }

    public static void log(String s, String message) {
        Log.wtf(s, message);
    }

    public static LatLng getLatLngFromAddress(String address) {
        LatLng latLng = null;
        try {
            List<Address> addresses = AppInitializer.getGeocoder().getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                latLng = new LatLng(
                        addresses.get(0).getLatitude(), addresses.get(0).getLongitude()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    public static LocationStatus getInitialLocationStatus() {
        return new LocationStatus(0.00, 0.00);
    }

    public static ParcelOrderTrack getInitialParcelOrderTrack() {
        return new ParcelOrderTrack(true, "Your order has been placed", String.valueOf(System.currentTimeMillis()), "Order Placed");
    }

    public static Query getDeliveredQuery() {
        return FirebaseFirestore.getInstance()
                .collection("PARCEL")
                .whereEqualTo("senderNumber", UserHelper.user.getMobile())
                .whereEqualTo("orderStatus", "Delivered")
                .orderBy("timeStamp");
    }

    public static Query getOngoingQuery() {
        return FirebaseFirestore.getInstance()
                .collection("PARCEL")
                .whereEqualTo("senderNumber", UserHelper.user.getMobile())
                .whereNotEqualTo("orderStatus", "Delivered")
                .orderBy("orderStatus")
                .orderBy("timeStamp");
    }


    public static void setHasUserAcknowledgedApprovalToTrue() {
        AppInitializer.getSharedPreferences().edit().putBoolean(Constants.IS_USER_ACKNOWLEDGED_APPROVAL_KEY, true).apply();
    }

    public static boolean getHasUserAcknowledgedApproval() {
        return AppInitializer.getSharedPreferences().getBoolean(Constants.IS_USER_ACKNOWLEDGED_APPROVAL_KEY, false);
    }

    public static String getRange(double distance) {
        String range;
        if (distance > 0.00 && distance <= 10.00) {
            range = "range_1";
        } else if (distance > 10.00 && distance <= 15.00) {
            range = "range_2";
        } else if (distance > 15.00 && distance <= 20.00) {
            range = "range_3";
        } else if (distance > 20.00 && distance <= 25.00) {
            range = "range_4";
        } else if (distance > 25.00 && distance <= 30.00) {
            range = "range_5";
        } else {
            range = "extraDistance";
        }
        return range;
    }

//    public static void checkIfMerchant(OnMerchantRegistrationCallback callback) {
//        FirebaseFirestore.getInstance()
//                .collection("USERS")
//                .document(Objects.requireNonNull(UserHelper.getUser().getUid()))
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//
//                            if (Objects.equals(documentSnapshot.get("hasApplied"), true) && Objects.equals(documentSnapshot.get("approved"), true)) {
//
//                                callback.onMerchantRegistered();
//
//                            } else if (Objects.requireNonNull(documentSnapshot.get("hasApplied")).equals(true) && Objects.requireNonNull(documentSnapshot.get("approved")).equals(false)) {
//                                callback.onMerchantPendingApproval();
//                            } else {
//                                callback.onMerchantUnregistered();
//                            }
//
//                        }
//                    }
//                });
//
//    }
}
