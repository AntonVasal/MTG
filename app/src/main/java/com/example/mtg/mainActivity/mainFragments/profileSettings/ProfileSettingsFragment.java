package com.example.mtg.mainActivity.mainFragments.profileSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileSettingsBindingImpl;
import com.example.mtg.mainActivity.mainFragments.MainFragment;
import com.example.mtg.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;


public class ProfileSettingsFragment extends Fragment {

    FragmentProfileSettingsBindingImpl binding;
    ProfileViewModel profileViewModel;
    FragmentManager fragmentManager;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        binding.settingsBackButton.setOnClickListener(view -> fragmentManager.beginTransaction()
                .replace(R.id.main_app_container, new MainFragment())
                .commit());
        binding.changeNicknameButton.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.main_app_container,new ProfileSettingsPasswordConfirmationFragment())
                .addToBackStack("").commit());
        binding.changeNameButton.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.main_app_container,new ProfileSettingsPasswordConfirmationFragment())
                .addToBackStack("").commit());
        binding.changeSurnameButton.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.main_app_container,new ProfileSettingsPasswordConfirmationFragment())
                .addToBackStack("").commit());
        binding.changeEmailButton.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.main_app_container,new ProfileSettingsPasswordConfirmationFragment())
                .addToBackStack("").commit());
        binding.changeCountryButton.setOnClickListener(view -> fragmentManager.beginTransaction().replace(R.id.main_app_container,new ProfileSettingsPasswordConfirmationFragment())
                .addToBackStack("").commit());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile_settings,container,false);
        binding.setLifecycleOwner(requireActivity());
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileViewModel);
        fragmentManager = requireActivity().getSupportFragmentManager();

        return binding.getRoot();
    }
}