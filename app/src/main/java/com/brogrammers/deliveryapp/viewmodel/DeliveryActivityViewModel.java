package com.brogrammers.deliveryapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brogrammers.deliveryapp.ApiInterface;
import com.brogrammers.deliveryapp.Result;
import com.brogrammers.deliveryapp.UserHelper;
import com.brogrammers.deliveryapp.callback.OnParcelOrderPlacedCallback;
import com.brogrammers.deliveryapp.model.ParcelDeliveryOrder;
import com.brogrammers.deliveryapp.model.PaymentStatus;
import com.brogrammers.deliveryapp.model.Receiver;
import com.brogrammers.deliveryapp.utility.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveryActivityViewModel extends ViewModel {

    private static final String TAG = "DeliveryActivityViewMod";

    private final ApiInterface apiInterface;

    private final MutableLiveData<ParcelDeliveryOrder> order;
    private final MutableLiveData<PaymentStatus> paymentStatus;
    private String categoryPricingDocumentName;

    public DeliveryActivityViewModel() {

        order = new MutableLiveData<>();

        paymentStatus = new MutableLiveData<>();

        order.setValue(
                new ParcelDeliveryOrder("", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        );

        paymentStatus.setValue(
                new PaymentStatus(false, "", "", "", "")
        );

        order.getValue().setSenderName(UserHelper.user.getName());
        order.getValue().setSenderNumber(UserHelper.user.getMobile());

        order.getValue().setOrderStatus("Order Placed"); // set order status initially

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://maps.googleapis.com/")
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    private Receiver receiver;

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;

        getPaymentStatus().getValue().setCashToCollect(receiver.getCashToCollect());
        getOrder().getValue().setAdditionalDirections(receiver.getAdditionalDirections()); // set additional directions
        getOrder().getValue().setReceiverName(receiver.getReceiverName()); // set receiver's name
        getOrder().getValue().setReceiverNumber(receiver.getReceiverPhoneNumber()); // set receiver's phone number
        getOrder().getValue().setDestinationAddress(receiver.getReceiverAddress()); // set destination address
    }

    private com.brogrammers.deliveryapp.Result.Distance distance = null;

    public com.brogrammers.deliveryapp.Result.Distance getDistance() {
        return distance;
    }

    private double deliveryCharge;

    public void setDeliveryDistance(double distance) {
        FirebaseFirestore.getInstance().collection("PRICE")
                .document(categoryPricingDocumentName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            double baseCharge = Double.parseDouble(documentSnapshot.getString(Utility.getRange(distance)));
                            deliveryCharge = baseCharge * distance;
                            getOrder().getValue().setTotalCharge(String.valueOf(deliveryCharge)); // set delivery charge
                        }
                    }
                });
    }

    public double getDeliveryCharge() {
        return deliveryCharge;
    }

    public void calculateDistance(String apiKey, String origin, String destination) {
        apiInterface.getDistance(apiKey, origin, destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull com.brogrammers.deliveryapp.Result result) {
                        distance = result.getRows().get(0).getElements().get(0).getDistance();
                        setDeliveryDistance(Utility.getDistance(distance));

                        getOrder().getValue().setOrderDistance(distance.getText());// set delivery distance
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }
                });
    }

    public MutableLiveData<ParcelDeliveryOrder> getOrder() {
        return order;
    }

    public MutableLiveData<PaymentStatus> getPaymentStatus() {
        return paymentStatus;
    }

    public void placeParcelDeliveryRequest(OnParcelOrderPlacedCallback parcelOrderPlacedCallback) {

        if (getOrder().getValue() != null) {

            String key = Utility.getDocumentIdGeneratedByFirestore();
            getOrder().getValue().setDocumentId(key);
            getOrder().getValue().setOrderId(key);
            getOrder().getValue().setTimeStamp(String.valueOf(System.currentTimeMillis()));
            FirebaseFirestore.getInstance().collection("PARCEL").document(key).set(getOrder().getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    parcelOrderPlacedCallback.onOrderPlaced(getOrder().getValue());
                }
            });

            // set payment status
            FirebaseFirestore.getInstance()
                    .collection("PARCEL")
                    .document(key)
                    .collection("payment status")
                    .document(key)
                    .set(getPaymentStatus().getValue());

            // set location status
            FirebaseFirestore.getInstance()
                    .collection("PARCEL")
                    .document(key)
                    .collection("rider location")
                    .document(key)
                    .set(Utility.getInitialLocationStatus());

            // set order track
            FirebaseFirestore.getInstance()
                    .collection("PARCEL")
                    .document(key)
                    .collection("order track")
                    .document()
                    .set(Utility.getInitialParcelOrderTrack());

        }
    }

    public void setCategoryPricingDocumentName(String documentName) {
        categoryPricingDocumentName = documentName;
    }
}
