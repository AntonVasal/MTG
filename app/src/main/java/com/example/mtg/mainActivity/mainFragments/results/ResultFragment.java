package com.example.mtg.mainActivity.mainFragments.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mtg.R;
import com.example.mtg.mainActivity.mainFragments.results.adapters.resultsTabLayoutAdapter.ResultsAdapter;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.AddFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.DivFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.MultiFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.SubFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ResultFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String [] fragmentResultsNames = getResources().getStringArray(R.array.types_of_task_for_tab);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new AddFragment());
        fragmentArrayList.add(new SubFragment());
        fragmentArrayList.add(new MultiFragment());
        fragmentArrayList.add(new DivFragment());

        ViewPager2 resultsViewPager = view.findViewById(R.id.results_view_pager);
        resultsViewPager.setAdapter(
                new ResultsAdapter(requireActivity(),fragmentArrayList)
        );

        TabLayout resultsTabs = view.findViewById(R.id.result_tabs);

        new TabLayoutMediator(
                resultsTabs,
                resultsViewPager,
                (tab, position) -> tab.setText(fragmentResultsNames[position])
        ).attach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

}