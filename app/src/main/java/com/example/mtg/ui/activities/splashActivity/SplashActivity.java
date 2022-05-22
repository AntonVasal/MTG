package com.example.mtg.ui.activities.splashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mtg.ui.activities.appIntroActivity.AppIntroActivity;
import com.example.mtg.ui.activities.logActivity.LogActivity;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (SharedPreferencesHolder.getInstance(this).isFirstOpening("isFirstOpening")){
            startActivity(new Intent(this, AppIntroActivity.class));
        }else{
            startActivity(new Intent(this, LogActivity.class));
        }
        finish();
    }
}