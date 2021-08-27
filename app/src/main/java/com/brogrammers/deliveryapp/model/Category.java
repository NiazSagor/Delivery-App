package com.brogrammers.deliveryapp.model;

public class Category {

    private String categoryIcon, categoryTitle, categoryDescription;

    public Category(String categoryIcon, String categoryTitle, String categoryDescription) {
        this.categoryIcon = categoryIcon;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
