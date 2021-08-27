package com.brogrammers.deliveryapp.model;

public class User {

    private String address, alternativeMobile, apartment;
    private boolean approved = false, hasApplied = false;
    private String city, dob, email, id, mobile, name, uid, zip;

    public User() {
    }

    public User(String address, String alternativeMobile, String apartment, boolean approved, String city, String dob, String email, boolean hasApplied, String id, String mobile, String name, String uid, String zip) {
        this.address = address;
        this.alternativeMobile = alternativeMobile;
        this.apartment = apartment;
        this.approved = approved;
        this.hasApplied = hasApplied;
        this.city = city;
        this.dob = dob;
        this.email = email;
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.uid = uid;
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAlternativeMobile(String alternativeMobile) {
        this.alternativeMobile = alternativeMobile;
    }

    public void setHasApplied(boolean hasApplied) {
        this.hasApplied = hasApplied;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public String getAlternativeMobile() {
        return alternativeMobile;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isHasApplied() {
        return hasApplied;
    }

    public String getCity() {
        return city;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getZip() {
        return zip;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
