package com.example.mtg.utility.networkDetection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

public class NetworkMonitorUtil extends ConnectivityManager.NetworkCallback {
    private final NetworkRequest mNetworkRequest;
    private final ConnectivityManager mConnectivityManager;
    private final NetworkStateManager mNetworkStateManager;

    public NetworkMonitorUtil(Context context){
        mNetworkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkStateManager = NetworkStateManager.getInstance();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        mNetworkStateManager.setNetworkConnectivityStatus(true);
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        mNetworkStateManager.setNetworkConnectivityStatus(false);
    }

    public  void registerNetworkCallbackEvents(){
        mConnectivityManager.registerNetworkCallback(mNetworkRequest,this);
    }

    public void checkNetworkState(){
        try {
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            mNetworkStateManager.setNetworkConnectivityStatus(networkInfo !=null && networkInfo.isConnected());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
