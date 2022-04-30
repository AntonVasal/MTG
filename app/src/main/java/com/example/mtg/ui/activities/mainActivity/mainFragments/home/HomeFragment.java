package com.example.mtg.ui.activities.mainActivity.mainFragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mtg.R;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.adapters.MainAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.DecimalsFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.IntegerFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.NaturalFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String [] fragmentNames = getResources().getStringArray(R.array.types_of_number_for_tab);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new NaturalFragment());
        fragmentArrayList.add(new IntegerFragment());
        fragmentArrayList.add(new DecimalsFragment());

        ViewPager2 mainViewPager = view.findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(
                new MainAdapter(requireActivity(),fragmentArrayList)
        );

        TabLayout mainTabs = view.findViewById(R.id.main_tabs);

        new TabLayoutMediator(
                mainTabs,
                mainViewPager,
                (tab, position) -> tab.setText(fragmentNames[position])
        ).attach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}