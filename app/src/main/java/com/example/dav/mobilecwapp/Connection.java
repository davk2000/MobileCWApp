package com.example.dav.mobilecwapp;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection {
    public static boolean isNetworkAvailable(Activity activity) { //  Connectivity manager is used which provides a method for checking the availability of a network in Android
        ConnectivityManager connectivity = (ConnectivityManager) activity // This method is called for when RSS, RSS2 & RSS3 classes start up
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}

