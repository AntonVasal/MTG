package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings;

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
import com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel.ProfileViewModel;

import java.util.Objects;


public class ProfileSettingsFragment extends Fragment {

    private FragmentProfileSettingsBindingImpl binding;
    private NavController navController;
    private static final String TYPE_FRAGMENTS = "typeFragments";
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        bundle = new Bundle();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        binding.settingsBackButton.setOnClickListener(view -> {
            if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsFragment ){
                navController.popBackStack();
            }
        });
        binding.changeNicknameButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS,1);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeNameButton.setOnClickListener(view ->{
            bundle.putInt(TYPE_FRAGMENTS,2);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeSurnameButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS,3);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeEmailButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS,4);
            letsGoToConfirmPassword(bundle);
        });
        binding.changeCountryButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS,5);
            letsGoToConfirmPassword(bundle);
        });
        binding.changePasswordButton.setOnClickListener(view -> {
            bundle.putInt(TYPE_FRAGMENTS,6);
            letsGoToConfirmPassword(bundle);
        });
    }


    private void letsGoToConfirmPassword(Bundle bundle){
        if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsFragment ){
            navController.navigate(R.id.action_profileSettingsFragment_to_profileSettingsPasswordConfirmationFragment,bundle);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_settings, container, false);
        binding.setLifecycleOwner(requireActivity());
        ProfileViewModel profileSettingsViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        binding.setViewModel(profileSettingsViewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}