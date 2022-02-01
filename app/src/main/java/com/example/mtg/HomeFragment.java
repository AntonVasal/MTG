package com.example.mtg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment {

    ViewPager2 mainViewPager;
    TabLayout mainTabs;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainViewPager = view.findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(
                new MainAdapter(this)
        );
        mainTabs = view.findViewById(R.id.main_tabs);
        new TabLayoutMediator(
                mainTabs,
                mainViewPager,
                (tab, position) -> {}
        ).attach();
        return view;

    }
}