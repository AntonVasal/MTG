package com.example.mtg.MainActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mtg.MainActivity.MainFragments.MainFragment;
import com.example.mtg.R;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_app_container, new MainFragment()).commit();
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