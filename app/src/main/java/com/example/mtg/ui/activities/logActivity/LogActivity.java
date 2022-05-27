package com.example.mtg.ui.activities.logActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.ui.activities.mainActivity.MainActivity;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LogActivity extends AppCompatActivity {
    private NavController navController;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(LogActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(this, aBoolean -> {
            if (!aBoolean){
               navController.navigate(R.id.connectionFragment);
            }else if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.connectionFragment){
                navController.popBackStack();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.connectionFragment){
            finish();
        }else {
            super.onBackPressed();
        }
    }

}