package com.example.mtg.ui.activities.logActivity.logFragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentResetPasswordBinding;
import com.example.mtg.ui.activities.logActivity.logViewModel.LogViewModel;
import com.example.mtg.utility.textwatchers.ValidationTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
    private NavController navController;
    private LogViewModel logViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.log_fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logViewModel = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        initListeners();
        textChanged();
    }

    private void textChanged() {
        ValidationTextWatcher textWatcher = new ValidationTextWatcher(binding.resetEmailEditText);
        binding.emailForReset.addTextChangedListener(textWatcher);
    }

    private void initListeners() {
        binding.resetPasswordButton.setOnClickListener(view -> resetPassword());

        binding.resetBackButton.setOnClickListener(view -> navController.popBackStack());
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(binding.emailForReset.getText()).toString().trim();

        if (email.isEmpty()) {
            setEmailError(getResources().getString(R.string.email_is_required));
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setEmailError(getResources().getString(R.string.pls_provide_valid_email));
            return;
        }
        binding.resetPasswordProgressBar.setVisibility(View.VISIBLE);
        binding.resetBackButton.setEnabled(false);
        binding.resetPasswordButton.setEnabled(false);

        logViewModel.sendResetPasswordEmail(email, status -> {
            switch (status) {
                case SUCCESS:
                    binding.resetPasswordProgressBar.setVisibility(View.GONE);
                    Snackbar.make(binding.getRoot(),R.string.check_your_email,Snackbar.LENGTH_LONG).show();
                    break;
                case ERROR:
                    binding.resetPasswordProgressBar.setVisibility(View.GONE);
                    setEmailError(getResources().getString(R.string.check_your_email));
                    Snackbar.make(binding.getRoot(),R.string.can_not_reset_password,Snackbar.LENGTH_LONG).show();
                    break;
            }
            binding.resetBackButton.setEnabled(true);
            binding.resetPasswordButton.setEnabled(true);
        });
    }

    private void setEmailError(String error){
        binding.resetEmailEditText.setError(error);
        binding.resetEmailEditText.requestFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}