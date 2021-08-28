package com.brogrammers.deliveryapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.brogrammers.deliveryapp.Constants;
import com.brogrammers.deliveryapp.R;
import com.brogrammers.deliveryapp.bottomsheet.DeliverySummaryBottomSheet;
import com.brogrammers.deliveryapp.bottomsheet.LocationSelectionBottomSheet;
import com.brogrammers.deliveryapp.bottomsheet.ReceiverDetailsBottomSheet;
import com.brogrammers.deliveryapp.callback.OnDeliverySummaryBottomSheetInteraction;
import com.brogrammers.deliveryapp.callback.OnLocationBottomSheetInteraction;
import com.brogrammers.deliveryapp.callback.OnParcelOrderPlacedCallback;
import com.brogrammers.deliveryapp.callback.OnReceiverDetailBottomSheetInteraction;
import com.brogrammers.deliveryapp.model.ParcelDeliveryOrder;
import com.brogrammers.deliveryapp.model.Receiver;
import com.brogrammers.deliveryapp.viewmodel.DeliveryActivityViewModel;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.vmadalin.easypermissions.EasyPermissions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DeliveryActivity extends AppCompatActivity implements OnMapReadyCallback, RoutingListener, EasyPermissions.PermissionCallbacks, OnLocationBottomSheetInteraction, OnReceiverDetailBottomSheetInteraction, OnDeliverySummaryBottomSheetInteraction, OnParcelOrderPlacedCallback {

    private static final String TAG = "DeliveryActivity";
    private DeliveryActivityViewModel model;
    private boolean isPermissionGranted;
    private GoogleMap mGoogleMap;
    private FloatingActionButton gps;
    private FusedLocationProviderClient mLocationClient;
    private List<Polyline> polylines = null;
    protected LatLng start = null;
    protected LatLng end = null;
    private Place place;
    private Location mLocation;
    private LocationSelectionBottomSheet locationSelectionBottomSheet;
    private DeliverySummaryBottomSheet deliverySummaryBottomSheet;
    private ReceiverDetailsBottomSheet receiverDetailsBottomSheet;

    private boolean isLineDrawn = false;

    private final List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    private Intent intent;

    private String payer = "Sender";
    private String paymentMethod = "Cash";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        model = new ViewModelProvider(this).get(DeliveryActivityViewModel.class);


        model.getOrder().getValue().setOrderCategory(
                getIntent().getStringExtra(Constants.ORDER_CATEGORY_KEY)
        );

        model.setCategoryPricingDocumentName(getIntent().getStringExtra(Constants.USER_TYPE_KEY));

        locationSelectionBottomSheet = new LocationSelectionBottomSheet(this, this);
        receiverDetailsBottomSheet = new ReceiverDetailsBottomSheet(this, this);
        deliverySummaryBottomSheet = new DeliverySummaryBottomSheet(this, this);

        intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountries(Arrays.asList("BD"))
                .build(this);

        gps = findViewById(R.id.gps_button);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);

        mLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (hasLocationPermission()) {
            isPermissionGranted = true;
        } else {
            requestLocationPermission();
        }

        if (isPermissionGranted) {
            if (isGPSenable()) {
                mapFragment.getMapAsync(this);
                getCurrentLocation();
            }
        }

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationSelectionBottomSheet.showBottomSheetDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        model.getOrder().getValue().setOrderCategory(
                getIntent().getStringExtra(Constants.ORDER_CATEGORY_KEY)
        );

        if (isGPSenable()
                &&
                !receiverDetailsBottomSheet.isSheetVisible()
                &&
                !deliverySummaryBottomSheet.isSheetVisible()) {
            locationSelectionBottomSheet.showBottomSheetDialog();
        }
    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            return true;
        } else {
            new AlertDialog.Builder(this).setTitle("GPS Permission")
                    .setMessage("Please Enable GPS")
                    .setPositiveButton("OK", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, Constants.GPS_REQUEST_CODE);
                    })).setCancelable(true).show();
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        mLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLocation = location;
                            start = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            getAddressFromLocation(mLocation.getLatitude(), mLocation.getLongitude());
                            gotoLocation(location.getLatitude(), location.getLongitude());
                        } else {
                            Log.d(TAG, "onSuccess: null");
                        }
                    }
                });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        mGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //To Do
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GPS_REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else if (requestCode == Constants.AUTOCOMPLETE_DESTINATION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                place = Autocomplete.getPlaceFromIntent(data);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18);
                mGoogleMap.moveCamera(cameraUpdate);
                locationSelectionBottomSheet.setDestinationLocation(place.getAddress());
                mGoogleMap.addMarker(new MarkerOptions().position(place.getLatLng()));
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == Constants.AUTOCOMPLETE_PICKUP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                start = place.getLatLng();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 18);
                mGoogleMap.moveCamera(cameraUpdate);
                locationSelectionBottomSheet.setPickUpLocation(place.getAddress());
                model.getOrder().getValue().setPickupAddress(place.getAddress());
                mGoogleMap.addMarker(new MarkerOptions().position(place.getLatLng()));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private boolean hasLocationPermission() {
        return EasyPermissions.hasPermissions(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(
                this,
                "This app requires location permission",
                Constants.PERMISSION_LOCATION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }

    @Override
    public void onPermissionsDenied(int i, @NotNull List<String> list) {
        if (EasyPermissions.somePermissionDenied(this, list.get(0))) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), "");
            intent.setData(uri);
            startActivity(intent);
        } else {
            requestLocationPermission();
        }
    }

    @Override
    public void onPermissionsGranted(int i, @NotNull List<String> list) {
        isPermissionGranted = true;
        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
    }


    public void findRoutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(DeliveryActivity.this, "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key(getString(R.string.api_key))  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onRoutingStart() {
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

//        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mGoogleMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);
                moveToBounds(polyline);
                mGoogleMap.getUiSettings().setAllGesturesEnabled(false);
                isLineDrawn = true;
            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("Pickup Point");
        mGoogleMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination point");
        mGoogleMap.addMarker(endMarker);
    }

    private void moveToBounds(Polyline p) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < p.getPoints().size(); i++) {
            builder.include(p.getPoints().get(i));
        }
        mGoogleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(builder.build(), 360)
        );
    }

    @Override
    public void onRoutingCancelled() {
        findRoutes(start, end);
    }

    private void drawPolyAndFindOutDistance() {
        if (locationSelectionBottomSheet.getDestinationLocation().equals("Dropoff Address")) {
            Toast.makeText(DeliveryActivity.this, "Please set a destination address first", Toast.LENGTH_SHORT).show();
        } else {
            end = place.getLatLng();
            mGoogleMap.clear();
            findRoutes(start, end);
            model.calculateDistance(
                    getString(R.string.api_key),
                    start.latitude + "," + start.longitude,
                    end.latitude + "," + end.longitude
            );
        }
    }

    @Override
    public void onReceiverDetailsConfirmed(Receiver receiver) {
        receiver.setReceiverAddress(place.getAddress());
        model.setReceiver(receiver);
        receiverDetailsBottomSheet.hideBottomSheet();
        deliverySummaryBottomSheet.showBottomSheet();
        setDeliverySummaryInformation();
    }

    private void setDeliverySummaryInformation() {
        deliverySummaryBottomSheet.setCashToCollect(model.getReceiver().getCashToCollect());
        deliverySummaryBottomSheet.setDistance(model.getDistance());
        deliverySummaryBottomSheet.setCharge(model.getDeliveryCharge());
        deliverySummaryBottomSheet.setReceiverAddress(model.getReceiver().getReceiverAddress());
        deliverySummaryBottomSheet.setReceiverName(model.getReceiver().getReceiverName());
        deliverySummaryBottomSheet.setReceiverNumber(model.getReceiver().getReceiverPhoneNumber());
    }

    @Override
    public void onPayerChanged(String payer) {
        Receiver receiver = model.getReceiver();
        receiver.setWillPay(payer.equals("Receiver"));
        model.setReceiver(receiver);
        this.payer = payer;
        model.getPaymentStatus().getValue().setPaidBy(payer);
    }

    @Override
    public void onPaymentMethodChanged(String paymentMethod) {
        if (TextUtils.equals("Digital", paymentMethod)) {
            Toast.makeText(this, "Additional 2% transaction charge is applicable for digital payment", Toast.LENGTH_LONG).show();
        }
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void onPickupRequested() {
        // if the sender is paying digitally
        if (paymentMethod.equals("digital") && payer.equals("Sender")) {
            double totalCharge = model.getDeliveryCharge() + (model.getDeliveryCharge() * 0.02);
            RequiredDataModel requiredDataModel = new RequiredDataModel("spaytest", "JehPNXF58rXs", "NOK" + Calendar.getInstance().getTimeInMillis(), totalCharge, "IPNToken");
            ShurjoPaySDK.getInstance().makePayment(this, SPayConstants.SdkType.TEST, requiredDataModel, this);
        } else if (paymentMethod.equals("Cash") && payer.equals("Receiver")) {
            // if the receiver will pay by cash
            model.getPaymentStatus().getValue().setPaid(false);
            model.getPaymentStatus().getValue().setPaymentMethod(paymentMethod);
            model.getPaymentStatus().getValue().setPaidBy(payer);
            model.getPaymentStatus().getValue().setPaymentMessage("Delivery charge will be paid by the receiver");
        } else if (paymentMethod.equals("Cash") && payer.equals("Sender")) {
            model.getPaymentStatus().getValue().setPaid(true);
            model.getPaymentStatus().getValue().setPaymentMethod(paymentMethod);
            model.getPaymentStatus().getValue().setPaidBy(payer);
            model.getPaymentStatus().getValue().setPaymentMessage("Delivery charge has been paid by the sender");
        }

        deliverySummaryBottomSheet.hideBottomSheet();

        model.placeParcelDeliveryRequest(this);
    }

    @Override
    public void onClickPickupAddress() {
        startActivityForResult(intent, Constants.AUTOCOMPLETE_PICKUP_REQUEST_CODE);
    }

    @Override
    public void onClickDestinationAddress() {
        startActivityForResult(intent, Constants.AUTOCOMPLETE_REQUEST_CODE);
    }

    @Override
    public void onRouteConfirmed() {
        if (isLineDrawn) {
            locationSelectionBottomSheet.hideBottomSheetDialog();
            receiverDetailsBottomSheet.showBottomSheet();
        } else {
            drawPolyAndFindOutDistance();
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                locationSelectionBottomSheet.setPickUpLocation(fetchedAddress.getAddressLine(0));
                model.getOrder().getValue().setPickupAddress(fetchedAddress.getAddressLine(0));
            } else {
                locationSelectionBottomSheet.setPickUpLocation("Set Pickup Address");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // if sender has made a successful digital payment
    @Override
    public void onSuccess(TransactionInfo transactionInfo) {
        assert transactionInfo != null;

        model.getPaymentStatus().getValue().setPaid(true);
        model.getPaymentStatus().getValue().setPaymentMethod(transactionInfo.getMethod());
        model.getPaymentStatus().getValue().setPaidBy("Sender");
        model.getPaymentStatus().getValue().setPaymentMessage("Delivery charge has been paid by the sender");
        model.placeParcelDeliveryRequest(this);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOrderPlaced(ParcelDeliveryOrder order) {
        if (order != null) {
            startActivity(
                    new Intent(this, ParcelOrderStatusActivity.class)
                            .putExtra(Constants.ORDER_CATEGORY_KEY, order.getOrderCategory())
                            .putExtra(Constants.DOCUMENT_ID_KEY, order.getDocumentId())
                            .putExtra(Constants.DESTINATION_ADDRESS_KEY, order.getDestinationAddress())
                            .putExtra(Constants.STATUS_ACTIVITY_LAUNCHING_FROM_KEY, "")
            );
            finish();
        }
    }
}