package com.ab.github_api_pq.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by andrewbortnichuk on 04/11/2017.
 */

public class InternetUtils {
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void connectWifi(Context testContext, boolean connect) {
        WifiManager wifi = (WifiManager) testContext.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(connect);
    }
}
