package com.brogrammers.deliveryapp;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UiHelper {
    private Context context;
    private static UiHelper instance;

    private UiHelper(Context context){
        this.context = context;
    }

    public static UiHelper getInstance(Application application){
        if (instance==null) instance = new UiHelper(application);
        return instance;
    }

    public boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

   /* public Dialog getNoNetworkDialog(Context activityContext){
        Dialog myDialog = new Dialog(activityContext);
        myDialog.setContentView(R.layout.sampleview_no_internet);
        myDialog.setCancelable(false);
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.show();
        return myDialog;
    }*/

    public Dialog getLoadingDialog(Context viewContext) {
        final Dialog myDialog = new Dialog(viewContext);
        myDialog.setContentView(R.layout.dialog_loading_animation);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return myDialog;
    }
}
