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
import com.example.mtg.databinding.DialogErrorOccurBinding;
import com.example.mtg.databinding.FragmentRegisterBinding;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.ui.activities.logActivity.logViewModel.LogViewModel;
import com.example.mtg.ui.dialogs.serviceDialogs.ErrorDialog;
import com.example.mtg.utility.textwatchers.ValidationTextWatcher;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private NavController navController;
    private LogViewModel logViewModel;
    private static final String DATA_NOT_PUSHED = "Data was not pushed to database";
    private ErrorDialog errorDialog;
    private DialogErrorOccurBinding errorOccurBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.log_fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        errorOccurBinding = DialogErrorOccurBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logViewModel = new ViewModelProvider(requireActivity()).get(LogViewModel.class);
        errorDialog = new ErrorDialog(requireActivity(),
                getResources().getString(R.string.loading_failed),
                getResources().getString(R.string.failed_to_upload_data),
                errorOccurBinding);
        initListeners();
        textSelected();
    }

    private void initListeners() {
        binding.registerButton.setOnClickListener(view -> registerUser());
        binding.backRegisterButton.setOnClickListener(view -> navController.popBackStack());
    }

    private void registerUser() {
        String email = Objects.requireNonNull(binding.email.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.password.getText()).toString().trim();
        String name = Objects.requireNonNull(binding.name.getText()).toString().trim();
        String surname = Objects.requireNonNull(binding.surname.getText()).toString().trim();
        String nickname = Objects.requireNonNull(binding.nickname.getText()).toString().trim();
        String country = binding.countryPicker.getSelectedCountryName();

        if (email.isEmpty()) {
            binding.registerEmailEditText.setError(getResources().getString(R.string.email_is_required));
            binding.registerEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registerEmailEditText.setError(getResources().getString(R.string.pls_provide_valid_email));
            binding.registerEmailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.registerPasswordEditText.setError(getResources().getString(R.string.password_is_required));
            binding.registerPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.registerPasswordEditText.setError(getResources().getString(R.string.min_password));
            binding.registerPasswordEditText.requestFocus();
            return;
        }

        if (nickname.isEmpty()) {
            binding.registerUserNicknameEditText.setError(getResources().getString(R.string.nickname_is_required));
            binding.registerUserNicknameEditText.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            binding.registerUserNameEditText.setError(getResources().getString(R.string.name_is_required));
            binding.registerUserNameEditText.requestFocus();
            return;
        }

        if (surname.isEmpty()) {
            binding.registerUserSurnameEditText.setError(getResources().getString(R.string.surname_is_required));
            binding.registerUserSurnameEditText.requestFocus();
            return;
        }

        binding.registerProgressBar.setVisibility(View.VISIBLE);
        binding.registerButton.setEnabled(false);
        binding.backRegisterButton.setEnabled(false);

        UserRegisterProfileModel user = new UserRegisterProfileModel(name, surname, nickname, email, country, "");

        logViewModel.createNewUser(user, password, userField -> {
            switch (userField.status){
                case SUCCESS:
                    binding.registerProgressBar.setVisibility(View.GONE);
                    navController.popBackStack();
                    break;
                case ERROR:
                    assert userField.message != null;
                    if (userField.message.equals(DATA_NOT_PUSHED)){
                        binding.registerProgressBar.setVisibility(View.GONE);
                    } else {
                        binding.registerProgressBar.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar.make(binding.getRoot(),R.string.registration_failed,Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    binding.registerButton.setEnabled(true);
                    binding.backRegisterButton.setEnabled(true);
            }
        });
    }

    private void textSelected() {
        ValidationTextWatcher emailTextWatcher = new ValidationTextWatcher(binding.registerEmailEditText);
        binding.email.addTextChangedListener(emailTextWatcher);

        ValidationTextWatcher passwordTextWatcher = new ValidationTextWatcher(binding.registerPasswordEditText);
        binding.password.addTextChangedListener(passwordTextWatcher);

        ValidationTextWatcher nameTextWatcher = new ValidationTextWatcher(binding.registerUserNameEditText);
        binding.name.addTextChangedListener(nameTextWatcher);

        ValidationTextWatcher nicknameTextWatcher = new ValidationTextWatcher(binding.registerUserNicknameEditText);
        binding.nickname.addTextChangedListener(nicknameTextWatcher);

        ValidationTextWatcher surnameTextWatcher = new ValidationTextWatcher(binding.registerUserSurnameEditText);
        binding.surname.addTextChangedListener(surnameTextWatcher);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        errorOccurBinding = null;
    }
}