package com.brogrammers.deliveryapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brogrammers.deliveryapp.Constants;
import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.adapter.OrderTrackAdapter;
import com.brogrammers.deliveryapp.callback.OnDocumentReferenceCallback;
import com.brogrammers.deliveryapp.callback.OnOrderTrackCollectionQueryCallback;
import com.brogrammers.deliveryapp.callback.OnRiderLocationUpdateCallback;
import com.brogrammers.deliveryapp.databinding.ActivityParcelOrderStatusBinding;
import com.brogrammers.deliveryapp.model.LocationStatus;
import com.brogrammers.deliveryapp.model.ParcelOrderTrack;
import com.brogrammers.deliveryapp.utility.Utility;
import com.brogrammers.deliveryapp.viewmodel.ParcelOrderStatusActivityViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class ParcelOrderStatusActivity extends AppCompatActivity implements OnMapReadyCallback, OnDocumentReferenceCallback, OnRiderLocationUpdateCallback, OnOrderTrackCollectionQueryCallback {

    private static final String TAG = "ParcelOrderStatusActivity";

    private ParcelOrderStatusActivityViewModel model;
    private ActivityParcelOrderStatusBinding binding;
    private GoogleMap googleMap;
    private OnRiderLocationUpdateCallback onRiderLocationUpdateCallback;
    private LocationStatus riderLocationStatus;
    private Marker riderMarker, destinationMarker;
    private OrderTrackAdapter orderTrackAdapter;
    private LatLng destination;
    private ListenerRegistration registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParcelOrderStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(this).get(ParcelOrderStatusActivityViewModel.class);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        onRiderLocationUpdateCallback = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * This activity is launched from 3 different points
         * 1st - when user places an parcel order
         * 2nd - when user comes from history
         * 3rd - when user comes from ongoing
         * So in order to check, we are using a flag
         * if it is "history", then we just want to show the order track timeline and hide the map
         * Otherwise, layout remains same and initialize both map and order track timeline
         * So in both the cases, order track timeline is common
         * that's why the method "model.getOrderTrackDocumentCallback()" stays outside the if condition
         * */

        if (getIntent().getStringExtra(Constants.STATUS_ACTIVITY_LAUNCHING_FROM_KEY).equals("history")) {
            binding.mapCard.setVisibility(View.GONE);
        } else {

            // as this order is ongoing, we are initiating the map
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);

            assert mapFragment != null;
            mapFragment.getMapAsync(this);

            // getting destination latlng from the destination address
            destination = Utility.getLatLngFromAddress(getIntent().getStringExtra(Constants.DESTINATION_ADDRESS_KEY));

            // as the order is ongoing we are calling latest rider location for the co ordinates
            if (riderLocationStatus == null) {
                model.getRiderLocationDocumentCallback(getIntent().getStringExtra(Constants.DOCUMENT_ID_KEY), this);
            }

        }

        model.getOrderTrackDocumentCallback(getIntent().getStringExtra(Constants.DOCUMENT_ID_KEY), this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // this condition is only for when we have to show map
        if (destination != null) {
            addDestinationMarker(
                    destination.latitude, destination.longitude
            );
        }
    }

    private void addDestinationMarker(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        googleMap.animateCamera(cameraUpdate);
        destinationMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
    }

    @Override
    public void onRiderLocationDocument(DocumentReference documentReference) {
        if (documentReference != null) {
            registration = documentReference.addSnapshotListener(riderLocationDocumentEventListener);
        }
    }

    @Override
    public void onRiderLocationDocumentNotFound() {
        Toast.makeText(this, "Your parcel is not yet been accepted by the rider", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRiderLocationUpdate(LocationStatus locationStatus) {

        if (locationStatus.getRiderLongitude() == 0.00) return;

        LatLng latLng = new LatLng(locationStatus.getRiderLatitude(), locationStatus.getRiderLongitude());

        // if rider location is already in the variable then update the value
        if (riderLocationStatus != null) {
            riderMarker.setPosition(latLng);
        } else {
            // if rider location is not in the variable make a new marker ana set on the map
            riderLocationStatus = locationStatus;
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            riderMarker = googleMap.addMarker(markerOptions);
            setMapBoundsOnFirstLocationUpdate();
        }
        Log.d(TAG, "onRiderLocationUpdate: latitude " + locationStatus.getRiderLatitude());
        Log.d(TAG, "onRiderLocationUpdate: longitude " + locationStatus.getRiderLongitude());
    }

    private void setMapBoundsOnFirstLocationUpdate() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(destinationMarker.getPosition());
        builder.include(riderMarker.getPosition());
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        googleMap.animateCamera(cu);
    }

    @Override
    public void onOrderTrackCollectionQuery(Query query) {
        if (query != null) {
            Log.d(TAG, "onOrderTrackCollectionQuery: not null query");
            FirestoreRecyclerOptions<ParcelOrderTrack> options = new FirestoreRecyclerOptions.Builder<ParcelOrderTrack>()
                    .setQuery(query, ParcelOrderTrack.class)
                    .build();

            orderTrackAdapter = new OrderTrackAdapter(options);
            orderTrackAdapter.startListening();
            orderTrackAdapter.notifyDataSetChanged();
            binding.recyclerViewTrackingItems.setAdapter(orderTrackAdapter);
        } else {
            Log.d(TAG, "onOrderTrackCollectionQuery: null query");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (orderTrackAdapter != null) {
            orderTrackAdapter.stopListening();
        }
        if (registration != null) {
            registration.remove();
        }
    }

    private final EventListener<DocumentSnapshot> riderLocationDocumentEventListener = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
            if (value != null && value.exists()) {

                onRiderLocationUpdateCallback.onRiderLocationUpdate(
                        value.toObject(LocationStatus.class)
                );

            }
        }
    };
}