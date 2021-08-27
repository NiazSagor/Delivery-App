package com.brogrammers.deliveryapp.model;

public class ParcelOrderTrack {

    private boolean checked;
    private String description, time, title;

    public ParcelOrderTrack() {
    }

    public ParcelOrderTrack(boolean checked, String description, String time, String title) {
        this.checked = checked;
        this.description = description;
        this.time = time;
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}
