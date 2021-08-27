package com.brogrammers.deliveryapp.model;

public class LocationStatus {

    private double riderLatitude, riderLongitude;

    public LocationStatus() {
    }


    public LocationStatus(double riderLatitude, double riderLongitude) {
        this.riderLatitude = riderLatitude;
        this.riderLongitude = riderLongitude;
    }

    public double getRiderLatitude() {
        return riderLatitude;
    }

    public void setRiderLatitude(double riderLatitude) {
        this.riderLatitude = riderLatitude;
    }

    public double getRiderLongitude() {
        return riderLongitude;
    }

    public void setRiderLongitude(double riderLongitude) {
        this.riderLongitude = riderLongitude;
    }
}
