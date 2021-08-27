package com.brogrammers.deliveryapp.model;

public class ParcelDeliveryOrderStatus {

    private boolean checked;
    private String description, time, title;

    public ParcelDeliveryOrderStatus(boolean checked, String description, String time, String title) {
        this.checked = checked;
        this.description = description;
        this.time = time;
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
