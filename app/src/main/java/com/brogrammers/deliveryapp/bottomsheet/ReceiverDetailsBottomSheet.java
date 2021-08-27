package com.brogrammers.deliveryapp.bottomsheet;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.callback.OnReceiverDetailBottomSheetInteraction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ReceiverDetailsBottomSheet implements View.OnClickListener {

    private final Context context;
    private final BottomSheetDialog bottomSheetDialog;
    private final View bottomSheetView;
    private final TextInputEditText receiverName, receiverNumber, additionalDirection, cashToCollectFromReceiver;
    private final MaterialButton receiverDetailsConfirmButton;
    private final OnReceiverDetailBottomSheetInteraction onReceiverDetailBottomSheetInteraction;

    public ReceiverDetailsBottomSheet(Context context, OnReceiverDetailBottomSheetInteraction onReceiverDetailBottomSheetInteraction) {
        this.context = context;
        this.onReceiverDetailBottomSheetInteraction = onReceiverDetailBottomSheetInteraction;
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(context).inflate(R.layout.receiver_details_bottom_sheet, null);

        receiverName = bottomSheetView.findViewById(R.id.receiver_name);
        receiverNumber = bottomSheetView.findViewById(R.id.receiver_phone_number);
        additionalDirection = bottomSheetView.findViewById(R.id.additional_direction);
        receiverDetailsConfirmButton = bottomSheetView.findViewById(R.id.confrim_receiver_details_button);
        cashToCollectFromReceiver = bottomSheetView.findViewById(R.id.cash_to_collect_from_receiver);
    }

    public void showBottomSheet() {
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
        receiverDetailsConfirmButton.setOnClickListener(this);
    }

    public void hideBottomSheet() {
        bottomSheetDialog.dismiss();
    }

    public boolean isSheetVisible() {
        return bottomSheetDialog.isShowing();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confrim_receiver_details_button) {
            if (!TextUtils.isEmpty(additionalDirection.getText().toString())
                    &&
                    !TextUtils.isEmpty(receiverName.getText().toString())
                    &&
                    !TextUtils.isEmpty(receiverNumber.getText().toString())
                    &&
                    !TextUtils.isEmpty(cashToCollectFromReceiver.getText().toString())
            ) {
                onReceiverDetailBottomSheetInteraction.onReceiverDetailsConfirmed(
                        new Receiver(
                                additionalDirection.getText().toString(),
                                cashToCollectFromReceiver.getText().toString(),
                                "",
                                receiverName.getText().toString(),
                                receiverNumber.getText().toString(),
                                false
                        )
                );
            } else {
                Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
