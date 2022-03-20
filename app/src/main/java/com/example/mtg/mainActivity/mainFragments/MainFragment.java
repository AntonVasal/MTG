package com.example.mtg.mainActivity.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.mainActivity.mainFragments.home.HomeFragment;
import com.example.mtg.mainActivity.mainFragments.profile.ProfileFragment;
import com.example.mtg.mainActivity.mainFragments.results.ResultFragment;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
//        BottomNavigationView bottomNavigationView = view.findViewById(R.id.main_bottom_navigation);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new HomeFragment()).commit();
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()){
//                case R.id.HomeFragment:
//                    selectedFragment = new HomeFragment();
//                    break;
//                case R.id.ResultFragment:
//                    selectedFragment = new ResultFragment();
//                    break;
//                case R.id.ProfileFragment:
//                    selectedFragment = new ProfileFragment();
//                    break;
//            }
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragmentContainerView,selectedFragment).commit();
//            return true;
//        });

        MeowBottomNavigation meowBottomNavigation = view.findViewById(R.id.main_bottom_navigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_emoji_events_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_person_24));

        meowBottomNavigation.show(1,true);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new HomeFragment()).commit();
        meowBottomNavigation.setOnClickMenuListener(model -> {
            Fragment selectedFragment = null;
            switch (model.getId()){
                case 1:
                    selectedFragment = new HomeFragment();
                    break;
                case 2:
                    selectedFragment = new ResultFragment();
                    break;
                case 3:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            assert selectedFragment != null;
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView,selectedFragment).commit();
            return null;
        });

        return view;
    }
}
