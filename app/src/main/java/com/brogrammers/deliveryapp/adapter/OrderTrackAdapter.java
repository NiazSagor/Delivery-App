package com.brogrammers.deliveryapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brogrammers.deliveryapp.CustomStringFormatter;
import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.model.ParcelOrderTrack;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class OrderTrackAdapter extends FirestoreRecyclerAdapter<ParcelOrderTrack, OrderTrackAdapter.OrderTrackHolder> {

    public OrderTrackAdapter(@NonNull @NotNull FirestoreRecyclerOptions<ParcelOrderTrack> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull OrderTrackHolder holder, int position, @NonNull @NotNull ParcelOrderTrack model) {
        holder.orderTitle.setText(model.getTitle());
        holder.orderDescription.setText(model.getDescription());
        holder.orderTime.setText(CustomStringFormatter.formatMillisecondsIntoDateAndTime(Long.parseLong(model.getTime())));
        holder.checkbox.setChecked(model.isChecked());
    }

    @NonNull
    @NotNull
    @Override
    public OrderTrackHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampleview_order_tracking_item, parent, false);
        return new OrderTrackHolder(view);
    }

    public static class OrderTrackHolder extends RecyclerView.ViewHolder {

        TextView orderTitle, orderTime, orderDescription;
        CheckBox checkbox;

        public OrderTrackHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            orderTitle = itemView.findViewById(R.id.textview_tittle);
            orderDescription = itemView.findViewById(R.id.textview_description);
            orderTime = itemView.findViewById(R.id.textview_time);
            checkbox = itemView.findViewById(R.id.checkbox);
        }
    }
}
