package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.DialogErrorOccurBinding;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.profileSettingsFragment.profileSettingsFragmentViewModel.ProfileSettingsViewModel;
import com.example.mtg.ui.dialogs.serviceDialogs.ErrorDialog;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class ChangeCountryFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private ErrorDialog errorDialog;
    private ProfileSettingsViewModel profileSettingsViewModel;
    private DialogErrorOccurBinding errorOccurBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileSettingsViewModel = new ViewModelProvider(requireActivity()).get(ProfileSettingsViewModel.class);
        detectConnection();
        initListeners();
        setViewData();
        errorOccurBinding = DialogErrorOccurBinding.inflate(getLayoutInflater());
        errorDialog = new ErrorDialog(requireActivity(),
                getResources().getString(R.string.update_error_text),
                getResources().getString(R.string.updating_failed),
                errorOccurBinding);
    }

    private void detectConnection() {
        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeCountryFragment) {
                        navController.popBackStack(R.id.profileSettingsFragment, true);
                    }
                });
    }

    private void setViewData() {
        binding.forChange.setVisibility(View.INVISIBLE);
        binding.changeEditText.setVisibility(View.INVISIBLE);
        binding.changeButton.setText(R.string.change_country);
        binding.countryPickerForChange.setVisibility(View.VISIBLE);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.ic_location_country, requireActivity().getTheme()));
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeCountryFragment) {
                navController.popBackStack();
            }
        });

        binding.changeButton.setOnClickListener(view -> {
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);
            binding.changeButton.setEnabled(false);
            binding.changeBackButton.setEnabled(false);
            String country = binding.countryPickerForChange.getSelectedCountryName();

            profileSettingsViewModel.updateUserCountry(country, status -> {
                switch (status) {
                    case SUCCESS:
                        if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeCountryFragment) {
                            binding.changeDataProgressBar.setVisibility(View.GONE);
                            Snackbar snackbar = Snackbar.make(binding.getRoot(),R.string.country_updated,Snackbar.LENGTH_LONG);
                            snackbar.show();
                            navController.popBackStack(R.id.profileSettingsPasswordConfirmationFragment, true);
                        }
                        break;
                    case ERROR:
                        binding.changeDataProgressBar.setVisibility(View.GONE);
                        errorDialog.show();
                        break;
                }
                binding.changeButton.setEnabled(true);
                binding.changeBackButton.setEnabled(true);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        errorOccurBinding = null;
    }
}