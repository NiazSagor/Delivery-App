package com.brogrammers.deliveryapp;

import android.app.Application;

public class ApplicationHelper {
    private static FirebaseDatabaseHelper databaseHelper;
    private static UiHelper uiHelper;
    private static UserHelper userHelper;

    public static void initializeHelpers(Application application) {
        databaseHelper = FirebaseDatabaseHelper.getInstance(application);
        databaseHelper.init();

        uiHelper = UiHelper.getInstance(application);

        userHelper = UserHelper.getInstance();
    }

    public static FirebaseDatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public static UiHelper getUtilsHelper() {
        return uiHelper;
    }

    public static UserHelper getUserHelper() {
        return userHelper;
    }
}
