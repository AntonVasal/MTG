package com.example.mtg.ui.activities.mainActivity.mainFragments.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.FragmentHomeBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.adapters.MainAdapter;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.DecimalsFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.IntegersFragment;
import com.example.mtg.ui.activities.mainActivity.mainFragments.home.typeTaskFragments.kotlin.NaturalsFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class HomeFragment extends BaseBindingFragment<FragmentHomeBinding> {

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