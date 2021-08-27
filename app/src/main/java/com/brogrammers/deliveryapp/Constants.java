package com.brogrammers.deliveryapp;

public class Constants {
    public static final String TAG = "DEBUG_MESSAGES";

    //firestore root
    public static final String CATEGORIES = "CATEGORIES";
    public static final String SUBCATEGORIES = "SUB-CATEGORIES";
    public static final String DB_USERS = "USERS";
    public static final String DB_SELLERS = "SELLERS";
    public static final String STORAGE_SELLERS = "SELLERS_PIC_NID";
    public static final String DB_PRODUCTS = "PRODUCTS";
    public static final String STORAGE_PRODUCTS = "PRODUCTS";
    public static final String DB_BANNERS = "BANNERS";

    public static final String DB_ORDERS = "ORDERS";
    public static final String DB_QUESTIONS = "QUESTIONS";  //root: QUESTIONS
    public static final String DB_REVIEWS = "REVIEWS";   //root: REVIEWS
    public static final String DB_TOTAL_RATING = "TOTAL_RATING";   //root: TOTAL_RATING-product_id
    public static final String DB_SHOP_REVIEWS = "REVIEWS_SHOP";   //root: REVIEWS
    public static final String DB_SHOP_TOTAL_RATING = "TOTAL_RATING_SHOP";   //root: TOTAL_RATING-product_id

    public static final String DB_CATEGORIES = "CATEGORIES";
    public static final String STORAGE_CATEGORIES = "CATEGORY_PICTURES";
    public static final String DB_SUB_CATEGORIES = "SUB_CATEGORIES";
    public static final String STORAGE_SUB_CATEGORIES = "SUB_CATEGORY_PICTURES";
    public static final String DB_SUB_SUB_CATEGORIES = "SUB_SUB_CATEGORIES";
    public static final String STORAGE_SUB_SUB_CATEGORIES = "SUB_SUB_CATEGORY_PICTURES";

    public static final String CART_FLAT_ALL = "ALL";

    public static final String DB_COUPONS = "COUPONS";
    public static final String DB_USED_COUPONS = "USED_COUPONS";
    public static final String NO_USED_COUPONS = "no_used_coupon";

    public static final String DB_HIGHLIGHTED_CATEGORIES = "HIGHLIGHTED_CATEGORIES";
    public static final String DB_HIGHLIGHTED_SUBCATEGORIES = "HIGHLIGHTED_SUB_CATEGORIES";


    public static final String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER",
            "NOVEMBER", "DECEMBER"};
    public static final String STORAGE_USER = "USER_PHOTOS";

    public static int GPS_REQUEST_CODE = 9001;
    public static int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static int AUTOCOMPLETE_DESTINATION_REQUEST_CODE = 1;
    public static int AUTOCOMPLETE_PICKUP_REQUEST_CODE = 2;
    public static int PERMISSION_LOCATION_REQUEST_CODE = 200;

    public static String DESTINATION_LATLNG_KEY = "destination-latlng-key";
    public static String DESTINATION_ADDRESS_KEY = "destination-address-key";
    public static String ORDER_CATEGORY_KEY = "order-category-key";
    public static String DOCUMENT_ID_KEY = "order-category-key";

    public static String STATUS_ACTIVITY_LAUNCHING_FROM_KEY = "status-activity-launching-from-key";
    public static String SHARED_PREFERENCES_KEY = "shared-preferences-key";
    public static String IS_USER_ACKNOWLEDGED_APPROVAL_KEY = "approval-acknowledge-key";
    public static String USER_TYPE_KEY = "user-type-key";


}
