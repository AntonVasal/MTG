package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgotPasswordFragment;

import android.os.Bundle;
import android.text.InputType;
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
import com.example.mtg.utility.textwatchers.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgotPasswordFragment.forgotPasswordViewModel.ForgotPasswordViewModel;
import com.example.mtg.utility.networkDetection.NetworkStateManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class PasswordYouRememberFragment extends Fragment {

    private NavController navController;
    private FragmentChangeDataBinding binding;
    private String password;
    private ForgotPasswordViewModel forgotPasswordViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forgotPasswordViewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);
        detectConnection();
        setViewsData();
        initListeners();
        textChanged();
    }

    private void detectConnection() {
        NetworkStateManager.getInstance().getNetworkConnectivityStatus().observe(getViewLifecycleOwner(),
                aBoolean -> {
                    if (!aBoolean && Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.passwordYouRememberFragment) {
                        navController.popBackStack(R.id.profileSettingsFragment, true);
                    }
                });
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.changeEditText);
        binding.forChange.addTextChangedListener(textWatcher);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.passwordYouRememberFragment) {
                navController.popBackStack();
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            password = Objects.requireNonNull(binding.forChange.getText()).toString().trim();
            if (password.isEmpty()) {
                userInputError(getResources().getString(R.string.password_is_required));
                return;
            }
            if (password.length() < 6) {
                userInputError(getResources().getString(R.string.min_password));
                return;
            }
            sendEmail();
        });
    }

    private void sendEmail() {
        binding.changeDataProgressBar.setVisibility(View.VISIBLE);
        binding.changeBackButton.setEnabled(false);
        binding.changeButton.setEnabled(false);
        forgotPasswordViewModel.sendResetPasswordEmail(status -> {
            Snackbar snackbar;
            switch (status) {
                case SUCCESS:
                    binding.changeDataProgressBar.setVisibility(View.GONE);
                    snackbar = Snackbar.make(binding.getRoot(), getResources().getString(R.string.check_your_email), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
                case ERROR:
                    binding.changeDataProgressBar.setVisibility(View.GONE);
                    snackbar = Snackbar.make(binding.getRoot(), getResources().getString(R.string.can_not_send_letter), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    break;
            }
            binding.changeBackButton.setEnabled(true);
            binding.changeButton.setEnabled(true);
        });
    }

    private void userInputError(String error) {
        binding.changeEditText.setError(error);
        binding.changeEditText.requestFocus();
    }

    private void setViewsData() {
        binding.changeEditText.setHint(R.string.password_you_remember);
        binding.changeImage.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().getResources(), R.drawable.ic_password_forgot, requireActivity().getTheme()));
        binding.forChange.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_security_24);
        binding.changeEditText.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        binding.changeButton.setText(R.string.confirm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
