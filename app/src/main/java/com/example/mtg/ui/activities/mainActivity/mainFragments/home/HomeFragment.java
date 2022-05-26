package com.example.mtg.ui.activities.mainActivity.mainFragments.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.FragmentHomeBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.adapters.MainAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.DecimalsFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.IntegersFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.NaturalsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding> {


    private MeowBottomNavigation meowBottomNavigation;

    @Override
    public void onStart() {
        super.onStart();
        meowBottomNavigation = requireActivity().findViewById(R.id.main_bottom_navigation);
        meowBottomNavigation.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_blue_dark, requireContext().getTheme()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] fragmentNames = getResources().getStringArray(R.array.types_of_number_for_tab);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new NaturalsFragment());
        fragmentArrayList.add(new IntegersFragment());
        fragmentArrayList.add(new DecimalsFragment());

        binding.mainViewPager.setAdapter(new MainAdapter(requireActivity(), fragmentArrayList));

        new TabLayoutMediator(
                binding.mainTabs,
                binding.mainViewPager,
                (tab, position) -> tab.setText(fragmentNames[position])
        ).attach();
        binding.mainTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        binding.mainTabs.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_blue_dark, requireContext().getTheme()));
                        meowBottomNavigation.setDefaultIconColor(Color.parseColor("#150AA2"));
                        meowBottomNavigation.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_blue_dark, requireContext().getTheme()));
                        break;
                    case 1:
                        binding.mainTabs.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_blue_bright, requireContext().getTheme()));
                        meowBottomNavigation.setDefaultIconColor(Color.parseColor("#2BE1D4"));
                        meowBottomNavigation.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_blue_bright, requireContext().getTheme()));
                        break;
                    case 2:
                        binding.mainTabs.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_orange, requireContext().getTheme()));
                        meowBottomNavigation.setDefaultIconColor(Color.parseColor("#FFBD08"));
                        meowBottomNavigation.setBackgroundColor(getResources().getColor(R.color.lottie_calculator_orange, requireContext().getTheme()));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}