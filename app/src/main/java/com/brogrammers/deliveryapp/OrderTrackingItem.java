package com.brogrammers.deliveryapp;

public class OrderTrackingItem {
    private String tittle,description,time;
    private boolean checked;

    public OrderTrackingItem(String tittle, String description, String time, boolean checked) {
        this.tittle = tittle;
        this.description = description;
        this.time = time;
        this.checked = checked;
    }

    public OrderTrackingItem(boolean checked, String description, String time, String title) {
        this.tittle = tittle;
        this.description = description;
        this.time = time;
        this.checked = checked;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
