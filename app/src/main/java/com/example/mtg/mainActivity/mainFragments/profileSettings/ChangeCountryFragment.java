package com.example.mtg.mainActivity.mainFragments.profileSettings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentChangeDataBinding;


public class ChangeCountryFragment extends Fragment {
    private FragmentChangeDataBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setViewData();
    }

    private void setViewData() {
        binding.changeEditText.setHint(R.string.country);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_location_on_24);
        binding.changeButton.setText(R.string.change_country);
    }

    private void initListeners() {
    }
}