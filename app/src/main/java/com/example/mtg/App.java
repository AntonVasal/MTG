package com.example.mtg;

import android.app.Application;

import com.example.mtg.utility.networkDetection.NetworkMonitorUtil;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkMonitorUtil networkMonitorUtil = new NetworkMonitorUtil(getApplicationContext());
        networkMonitorUtil.checkNetworkState();
        networkMonitorUtil.registerNetworkCallbackEvents();
    }
}
