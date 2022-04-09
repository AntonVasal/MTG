package com.example.mtg.mainActivity.mainFragments.profileSettings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileSettingsBindingImpl;
import com.example.mtg.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;

import java.util.Objects;


public class ProfileSettingsFragment extends Fragment {

    FragmentProfileSettingsBindingImpl binding;
    ProfileViewModel profileSettingsViewModel;
    NavController navController;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        binding.settingsBackButton.setOnClickListener(view -> navController.popBackStack());
        binding.changeNicknameButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("typeFragments",1);
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        });
        binding.changeNameButton.setOnClickListener(view ->{
            Bundle bundle = new Bundle();
            bundle.putInt("typeFragments",2);
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        });
        binding.changeSurnameButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("typeFragments",3);
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        });
        binding.changeEmailButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("typeFragments",4);
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        });
        binding.changeCountryButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("typeFragments",5);
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_settings, container, false);
        binding.setLifecycleOwner(requireActivity());
        profileSettingsViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileSettingsViewModel);

        return binding.getRoot();
    }
}