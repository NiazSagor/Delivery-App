package com.brogrammers.deliveryapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Geocoder;

import com.google.android.libraries.places.api.Places;
import com.google.firebase.FirebaseApp;

import java.util.Locale;

public class AppInitializer extends Application {

    private static Geocoder geocoder;

    public static Geocoder getGeocoder() {
        return geocoder;
    }

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        geocoder = new Geocoder(this, Locale.ENGLISH);

        FirebaseApp.initializeApp(this);

        ApplicationHelper.initializeHelpers(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.forLanguageTag("bn_BD"));
        }
    }

}
