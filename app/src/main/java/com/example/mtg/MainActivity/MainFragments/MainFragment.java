package com.example.mtg.MainActivity.MainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mtg.MainActivity.MainFragments.Home.HomeFragment;
import com.example.mtg.MainActivity.MainFragments.Profile.ProfileFragment;
import com.example.mtg.MainActivity.MainFragments.Results.ResultFragment;
import com.example.mtg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.main_bottom_navigation);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new HomeFragment()).commit();
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
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,selectedFragment).commit();
            return true;
        });

        return view;
    }
}
