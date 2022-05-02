package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgotPasswordFragment;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.example.mtg.core.ValidationTextWatcher;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgotPasswordFragment.forgotPasswordViewModel.ForgotPasswordViewModel;
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
        setViewsData();
        initListeners();
        textChanged();
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
                binding.changeEditText.setError(getResources().getString(R.string.password_is_required));
                binding.changeEditText.requestFocus();
                return;
            }
            if (password.length() < 6) {
                binding.changeEditText.setError(getResources().getString(R.string.min_password));
                binding.changeEditText.requestFocus();
                return;
            }
            binding.changeDataProgressBar.setVisibility(View.VISIBLE);
            forgotPasswordViewModel.sendResetPasswordEmail(status -> {
                switch (status) {
                    case SUCCESS:
                        binding.changeDataProgressBar.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        Log.e("Forgot","Failed");
                        binding.changeDataProgressBar.setVisibility(View.GONE);
                        break;
                }
            });
        });
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
