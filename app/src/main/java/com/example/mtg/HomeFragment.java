package com.example.mtg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewPager = view.findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(
                new MainAdapter(getActivity())
        );
        mainTabs = view.findViewById(R.id.main_tabs);
        new TabLayoutMediator(
                mainTabs,
                mainViewPager,
                (tab, position) -> tab.setText(((MainAdapter)(mainViewPager.getAdapter())).fragmentNames[position])
        ).attach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
}