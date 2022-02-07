package com.example.mtg;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mtg.MainFragments.Home.HomeFragment;
import com.example.mtg.MainFragments.Profile.ProfileFragment;
import com.example.mtg.MainFragments.Results.ResultFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.HomeFragment:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.ResultFragment:
                    selectedFragment = new ResultFragment();
                    break;
                case R.id.ProfileFragment:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,selectedFragment).commit();
            return true;
        });

    }

//    @Override
//    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onPostCreate(savedInstanceState, persistentState);
//
//
//        NavController navController = Navigation.findNavController(this,R.id.fragmentContainerView);
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.HomeFragment, R.id.ResultFragment, R.id.ProfileFragment
//        ).build();
//        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
//
//
//
//
//    }
}