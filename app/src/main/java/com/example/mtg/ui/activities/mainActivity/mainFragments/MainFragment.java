package com.example.mtg.ui.activities.mainActivity.mainFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.HomeFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profile.ProfileFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.results.ResultFragment;

import java.util.ArrayList;

public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        MeowBottomNavigation meowBottomNavigation = view.findViewById(R.id.main_bottom_navigation);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

        meowBottomNavigation.add(new MeowBottomNavigation.Model(0,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_emoji_events_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_person_24));

        meowBottomNavigation.show(0,true);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new ResultFragment());
        fragmentArrayList.add(new ProfileFragment());

        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragmentArrayList.get(0)).commit();

        meowBottomNavigation.setOnClickMenuListener(model -> {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,fragmentArrayList.get(model.getId())).commit();
            return null;
        });

        return view;
    }
}
