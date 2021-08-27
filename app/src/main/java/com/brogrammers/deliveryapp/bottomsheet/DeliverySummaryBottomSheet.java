package com.brogrammers.deliveryapp.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.callback.OnDeliverySummaryBottomSheetInteraction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

public class DeliverySummaryBottomSheet implements View.OnClickListener {

    private static final String TAG = "DeliverySummaryBottomSh";

    private boolean isCashPaymentClicked = false;

    private final Context context;
    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private final MaterialButton addValueButton, requestDeliveryButton;
    private final TextView distance, charge, receiverName, receiverAddress, receiverNumber, cashToCollect;
    private final LinearLayout digitalPayment, cashPayment;
    private final RadioButton sender, receiver;
    private final OnDeliverySummaryBottomSheetInteraction onDeliverySummaryBottomSheetInteraction;

    public DeliverySummaryBottomSheet(Context context, OnDeliverySummaryBottomSheetInteraction onDeliverySummaryBottomSheetInteraction) {
        this.onDeliverySummaryBottomSheetInteraction = onDeliverySummaryBottomSheetInteraction;
        this.context = context;
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.delivery_summary_bottom_sheet, null);

        addValueButton = bottomSheetView.findViewById(R.id.add_value);
        requestDeliveryButton = bottomSheetView.findViewById(R.id.delivery_request_button);
        distance = bottomSheetView.findViewById(R.id.distance);
        charge = bottomSheetView.findViewById(R.id.charge);
        cashToCollect = bottomSheetView.findViewById(R.id.cash_to_collect_from_receiver);
        receiverName = bottomSheetView.findViewById(R.id.receiver_name);
        receiverAddress = bottomSheetView.findViewById(R.id.receiver_address);
        receiverNumber = bottomSheetView.findViewById(R.id.receiver_phone_number);
        digitalPayment = bottomSheetView.findViewById(R.id.digital_payment);
        cashPayment = bottomSheetView.findViewById(R.id.cash_payment);
        sender = bottomSheetView.findViewById(R.id.sender);
        receiver = bottomSheetView.findViewById(R.id.receiver);

        sender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onDeliverySummaryBottomSheetInteraction.onPayerChanged("sender");
                } else {
                    onDeliverySummaryBottomSheetInteraction.onPayerChanged("receiver");
                }
            }
        });


        digitalPayment.setOnClickListener(this);
        cashPayment.setOnClickListener(this);
        requestDeliveryButton.setOnClickListener(this);
    }

    public void showBottomSheet() {
        onDeliverySummaryBottomSheetInteraction.onPayerChanged("sender");
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
    }

    public void hideBottomSheet() {
        bottomSheetDialog.dismiss();
    }

    public boolean isSheetVisible() {
        return bottomSheetDialog.isShowing();
    }

    @Override
    public void onClick(View v) {
        digitalPayment.setBackground(null);
        cashPayment.setBackground(null);
        if (v.getId() == R.id.digital_payment) {
            digitalPayment.setBackground(ContextCompat.getDrawable(context, R.drawable.button_border));
            onDeliverySummaryBottomSheetInteraction.onPaymentMethodChanged("Digital");
        } else if (v.getId() == R.id.cash_payment) {
            cashPayment.setBackground(ContextCompat.getDrawable(context, R.drawable.button_border));
            onDeliverySummaryBottomSheetInteraction.onPaymentMethodChanged("Cash");
        } else if (v.getId() == R.id.delivery_request_button) {
            onDeliverySummaryBottomSheetInteraction.onPickupRequested();
        }
    }

    public void setCashToCollect(String amount) {
        cashToCollect.setText(
                String.format("%s BDT", amount)
        );
    }

    public void setReceiverName(String name) {
        receiverName.setText(name);
    }

    public void setReceiverAddress(String address) {
        receiverAddress.setText(address);
    }

    public void setReceiverNumber(String number) {
        receiverNumber.setText(number);
    }

    public void setDistance(Result.Distance d) {
        distance.setText(d.getText());
        //setCharge(Utility.getCharge(d));
    }

    @SuppressLint("DefaultLocale")
    public void setCharge(double cost) {
        charge.setText(
                String.format("%.2f BDT", cost)
        );
    }
}
