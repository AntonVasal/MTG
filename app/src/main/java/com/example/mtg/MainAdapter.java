package com.example.mtg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainAdapter extends FragmentStateAdapter {

    public final String [] fragmentNames = new String[]{"Naturals","Integers","Decimals"};


    public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new NaturalFragment();
            case 1:
                return new IntegerFragment();
            default:
                return new DecimalsFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
