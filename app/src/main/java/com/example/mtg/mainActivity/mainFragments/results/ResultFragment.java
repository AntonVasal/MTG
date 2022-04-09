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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class ResultFragment extends Fragment {
    private ViewPager2 resultsViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultsViewPager = view.findViewById(R.id.results_view_pager);
        resultsViewPager.setAdapter(
                new ResultsAdapter(requireActivity())
        );
        TabLayout resultsTabs = view.findViewById(R.id.result_tabs);
        new TabLayoutMediator(
                resultsTabs,
                resultsViewPager,
                (tab, position) -> tab.setText(((ResultsAdapter)(Objects.requireNonNull(resultsViewPager.getAdapter()))).fragmentResultsNames[position])
        ).attach();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_result, container, false);
    }

}