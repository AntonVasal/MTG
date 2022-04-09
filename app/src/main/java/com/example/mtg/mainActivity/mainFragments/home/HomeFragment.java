package com.example.mtg.mainActivity.mainFragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mtg.mainActivity.mainFragments.home.adapters.MainAdapter;
import com.example.mtg.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;


public class HomeFragment extends Fragment {

    private ViewPager2 mainViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewPager = view.findViewById(R.id.main_view_pager);
        mainViewPager.setAdapter(
                new MainAdapter(requireActivity())
        );
        TabLayout mainTabs = view.findViewById(R.id.main_tabs);
        new TabLayoutMediator(
                mainTabs,
                mainViewPager,
                (tab, position) -> tab.setText(((MainAdapter)(Objects.requireNonNull(mainViewPager.getAdapter()))).fragmentNames[position])
        ).attach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}