package com.example.mtg.utility.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHolder {

    private static SharedPreferencesHolder instance;
    private SharedPreferences userData;
    private SharedPreferences.Editor sharedPrefEditor;
    public static final String SHARED_NAME = "Data";
    public static final String DEFAULT_STRING = "Empty";

    public static SharedPreferencesHolder getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesHolder();
            instance.editShared(context);
        }
        return instance;
    }

    private void editShared(Context context) {
        userData = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        sharedPrefEditor = userData.edit();
    }

    private SharedPreferencesHolder() {
    }

    public void setData(String key, String value) {
        sharedPrefEditor.putString(key, value);
        sharedPrefEditor.apply();
    }

    public String getData(String key) {
        return userData.getString(key, DEFAULT_STRING);
    }

    public boolean isFirstOpening(String key) {
        return userData.getBoolean(key, true);
    }

    public void setIsFirstOpening(String key, boolean value){
        sharedPrefEditor.putBoolean(key, value);
        sharedPrefEditor.apply();
    }

}
