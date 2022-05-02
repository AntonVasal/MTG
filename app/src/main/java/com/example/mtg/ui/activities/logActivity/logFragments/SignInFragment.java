package com.example.mtg.ui.activities.logActivity.logFragments;

import android.content.Intent;
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
import com.example.mtg.core.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentSignInBinding;
import com.example.mtg.ui.activities.logActivity.logViewModel.LogViewModel;
import com.example.mtg.ui.activities.mainActivity.MainActivity;

import java.util.Objects;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
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
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logViewModel = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        initListeners();
        textSelected();
    }

    private void textSelected() {
        ValidationTextWatcher passwordTextWatcher = new ValidationTextWatcher(binding.signInPasswordEditText);
        binding.password.addTextChangedListener(passwordTextWatcher);
        ValidationTextWatcher emailTextWatcher = new ValidationTextWatcher(binding.signInEmailEditText);
        binding.email.addTextChangedListener(emailTextWatcher);
    }

    private void initListeners() {
        binding.registerTextView.setOnClickListener(view -> {
            clearTextView();
            navController.navigate(R.id.action_signInFragment_to_registerFragment);
        });

        binding.signInButton.setOnClickListener(view -> userLogin());

        binding.resetPassword.setOnClickListener(view -> {
            clearTextView();
            navController.navigate(R.id.action_signInFragment_to_resetPasswordFragment);
        });
    }

    private void clearTextView() {
        binding.email.setText("");
        binding.password.setText("");
    }

    private void userLogin() {
        String email = Objects.requireNonNull(binding.email.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.password.getText()).toString().trim();

        if (email.isEmpty()) {
            binding.signInEmailEditText.setError(getResources().getString(R.string.email_is_required));
            binding.signInEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.signInEmailEditText.setError(getResources().getString(R.string.pls_provide_valid_email));
            binding.signInEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            binding.signInPasswordEditText.setError(getResources().getString(R.string.password_is_required));
            binding.signInPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.signInPasswordEditText.setError(getResources().getString(R.string.min_password));
            binding.signInPasswordEditText.requestFocus();
            return;
        }

        binding.signInProgressBar.setVisibility(View.VISIBLE);

        logViewModel.authUser(email, password, status -> {
            switch (status){
                case SUCCESS:
                    binding.signInProgressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                    break;
                case ERROR:
                    binding.signInProgressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}