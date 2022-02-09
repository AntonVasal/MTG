package com.example.mtg.MainActivity.MainFragments.Results.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mtg.MainActivity.MainFragments.Results.TypesOfResultFragments.AddFragment;
import com.example.mtg.MainActivity.MainFragments.Results.TypesOfResultFragments.DivFragment;
import com.example.mtg.MainActivity.MainFragments.Results.TypesOfResultFragments.MultiFragment;
import com.example.mtg.MainActivity.MainFragments.Results.TypesOfResultFragments.SubFragment;

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
