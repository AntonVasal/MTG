package com.example.mtg.utility.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHolder {

    private static SharedPreferencesHolder instance;
    private SharedPreferences userData;
    private SharedPreferences.Editor sharedPrefEditor;
    public static final String SHARED_NAME = "Data";
    public static final String DEFAULT_STRING = "Empty";

    public static void createInstance(Context context){
        if (instance==null){
            instance = new SharedPreferencesHolder();
            instance.editShared(context);
        }
    }

    public static SharedPreferencesHolder getInstance() {
        return instance;
    }

    private void editShared(Context context) {
        userData = context.getSharedPreferences(SHARED_NAME,Context.MODE_PRIVATE);
        sharedPrefEditor = userData.edit();
    }

    private SharedPreferencesHolder(){}

    public void setData(String key, String value){
        sharedPrefEditor.putString(key,value);
        sharedPrefEditor.commit();
    }

    public String getData(String key){
        return userData.getString(key,DEFAULT_STRING);
    }

}
