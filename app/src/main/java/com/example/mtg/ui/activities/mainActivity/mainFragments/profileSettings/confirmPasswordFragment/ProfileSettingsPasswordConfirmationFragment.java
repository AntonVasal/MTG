package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.confirmPasswordFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.utility.textwatchers.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentProfileSettingsPasswordConfirmationBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.confirmPasswordFragment.confirmPasswordViewModel.ConfirmPasswordViewModel;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class ProfileSettingsPasswordConfirmationFragment extends Fragment {

    private FragmentProfileSettingsPasswordConfirmationBinding binding;
    private String password;
    private NavController navController;
    private NavDirections navDirections;
    private static final String TYPE_FRAGMENTS = "typeFragments";
    private int typeFragments;
    private ConfirmPasswordViewModel confirmPasswordViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        typeFragments = getArguments().getInt(TYPE_FRAGMENTS);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileSettingsPasswordConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmPasswordViewModel = new ViewModelProvider(requireActivity()).get(ConfirmPasswordViewModel.class);
        detectConnection();
        initListeners();
        textChanged();
    }

    private void detectConnection() {
        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                        navController.popBackStack(R.id.profileSettingsFragment, true);
                    }
                });
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.confirmPasswordEditText);
        binding.passwordForConfirm.addTextChangedListener(textWatcher);
    }

    private void initListeners() {
        binding.forgetPassword.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                navController.navigate(R.id.action_profileSettingsPasswordConfirmationFragment_to_passwordYouRememberFragment);
            }
        });

        binding.confirmPasswordBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                navController.popBackStack();
            }
        });

        binding.confirmPasswordButton.setOnClickListener(view -> {
            password = Objects.requireNonNull(binding.passwordForConfirm.getText()).toString().trim();
            if (password.isEmpty()) {
                userInputError(getResources().getString(R.string.password_is_required));
                return;
            }
            if (password.length() < 6) {
                userInputError(getResources().getString(R.string.min_password));
                return;
            }
            confirmPassword();
        });
    }

    private void confirmPassword() {
        binding.confirmPasswordProgressBar.setVisibility(View.VISIBLE);
        binding.confirmPasswordButton.setEnabled(false);
        binding.forgetPassword.setEnabled(false);
        binding.confirmPasswordBackButton.setEnabled(false);
        confirmPasswordViewModel.reAuthCurrentUser(password, status -> {
            switch (status) {
                case SUCCESS:
                    chooseDirection();
                    if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.profileSettingsPasswordConfirmationFragment) {
                        binding.confirmPasswordProgressBar.setVisibility(View.GONE);
                        binding.passwordForConfirm.setText("");
                        navController.navigate(navDirections);
                    }
                    break;
                case ERROR:
                    binding.confirmPasswordProgressBar.setVisibility(View.GONE);
                    userInputError(getResources().getString(R.string.check_password));
                    Snackbar snackbar = Snackbar.make(binding.getRoot(),getResources().getString(R.string.confirmation_failed),Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
            }
            binding.confirmPasswordButton.setEnabled(true);
            binding.forgetPassword.setEnabled(true);
            binding.confirmPasswordBackButton.setEnabled(true);
        });
    }

    private void userInputError(String error){
        binding.confirmPasswordEditText.setError(error);
        binding.confirmPasswordEditText.requestFocus();
    }

    private void chooseDirection() {
        switch (typeFragments) {
            case 1:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                        .actionProfileSettingsPasswordConfirmationFragmentToChangeNicknameFragment();
                break;
            case 2:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                        .actionProfileSettingsPasswordConfirmationFragmentToChangeNameFragment();
                break;
            case 3:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                        .actionProfileSettingsPasswordConfirmationFragmentToChangeSurnameFragment();
                break;
            case 4:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                        .actionProfileSettingsPasswordConfirmationFragmentToChangeEmailFragment();
                break;
            case 5:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                        .actionProfileSettingsPasswordConfirmationFragmentToChangeCountryFragment();
                break;
            case 6:
                navDirections = ProfileSettingsPasswordConfirmationFragmentDirections.
                        actionProfileSettingsPasswordConfirmationFragmentToChangePasswordFragment();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}