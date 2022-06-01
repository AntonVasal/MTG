package com.example.mtg;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.mtg.retrofit.RetrofitClient;
import com.example.mtg.utility.networkDetection.NetworkMonitorUtil;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

    public static App instance;
    public static final String BASE_URl = "https://api.nasa.gov/planetary/";

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        instance = this;

        NetworkMonitorUtil networkMonitorUtil = new NetworkMonitorUtil(getApplicationContext());
        networkMonitorUtil.checkNetworkState();
        networkMonitorUtil.registerNetworkCallbackEvents();

        SharedPreferencesHolder.getInstance(getApplicationContext());

        RetrofitClient.getInstance(BASE_URl,getApplicationContext());

    }
}
