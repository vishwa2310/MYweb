package com.myweb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class GlobalObject {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        } else {
            return false;
        }
    }


}
