package com.example.mtg.mainActivity.mainFragments.results.adapters.resultsTabLayoutAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.AddFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.DivFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.MultiFragment;
import com.example.mtg.mainActivity.mainFragments.results.typesOfResultFragments.SubFragment;

public class ResultsAdapter extends FragmentStateAdapter {

    public final String [] fragmentResultsNames = new String[]{"Add","Sub","Multi","Div"};

    public ResultsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AddFragment();
            case 1:
                return new SubFragment();
            case 2:
                return new MultiFragment();
            default:
                return new DivFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
