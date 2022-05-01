package com.example.mtg.ui.activities.logActivity.logFragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.core.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentResetPasswordBinding;
import com.example.mtg.ui.activities.logActivity.logViewModel.LogViewModel;

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
        View view = binding.getRoot();
        logViewModel = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            binding.resetEmailEditText.setError(getResources().getString(R.string.email_is_required));
            binding.resetEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.resetEmailEditText.setError(getResources().getString(R.string.pls_provide_valid_email));
            binding.resetEmailEditText.requestFocus();
            return;
        }
        binding.resetPasswordProgressBar.setVisibility(View.VISIBLE);

        logViewModel.sendResetPasswordEmail(email, status -> {
            switch (status) {
                case SUCCESS:
                    binding.resetPasswordProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Please, check your email to reset password!", Toast.LENGTH_LONG)
                            .show();
                    break;
                case ERROR:
                    binding.resetPasswordProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Something went wrong. Please, try again!", Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}