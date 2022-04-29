package com.example.mtg.utility;

import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

public class NetworkStateManager {
    private static NetworkStateManager INSTANCE;
    private static final MutableLiveData<Boolean> activeNetworkStatus = new MutableLiveData<>();

    private NetworkStateManager(){}

    public static synchronized NetworkStateManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new NetworkStateManager();
        }
        return INSTANCE;
    }

    public void setNetworkConnectivityStatus(boolean connectivityStatus){
        if (Looper.myLooper() == Looper.getMainLooper()){
            activeNetworkStatus.setValue(connectivityStatus);
        }else{
            activeNetworkStatus.postValue(connectivityStatus);
        }
    }

    public MutableLiveData<Boolean> getNetworkConnectivityStatus(){
        return activeNetworkStatus;
    }
}
