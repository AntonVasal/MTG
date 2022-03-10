package com.example.mtg.logActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mtg.logActivity.logFragments.SignInFragment;
import com.example.mtg.mainActivity.MainActivity;
import com.example.mtg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(LogActivity.this, MainActivity.class));
            finish();
        } else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.log_activity_container,new SignInFragment()).commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    }
}