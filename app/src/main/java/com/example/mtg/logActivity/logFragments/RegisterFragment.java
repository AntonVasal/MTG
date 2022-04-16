package com.example.mtg.logActivity.logFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentRegisterBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private String userID;
    private NavController navController;
    private static final String USERS = "users";
    private static final String NO_IMAGE = "users";

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
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        new Thread(() -> mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        UserRegisterProfileModel user = new UserRegisterProfileModel(name, surname, nickname, email, country, NO_IMAGE);

                        mFirebaseFirestore.collection(USERS).document(userID).set(user).addOnCompleteListener(
                                task1 -> {
                                    if (task1.isSuccessful()) {
                                        requireActivity().runOnUiThread(() -> {
                                            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.registerFragment) {
                                                navController.popBackStack();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    } else {
                        Toast.makeText(getContext(), "Registration failed! Please, try again!", Toast.LENGTH_LONG).show();
                    }
                })).start();
    }

    private void textSelected() {
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.registerEmailEditText.getError() != null) {
                    binding.registerEmailEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.registerPasswordEditText.getError()!=null){
                    binding.registerPasswordEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.registerUserNameEditText.getError() != null) {
                    binding.registerUserNameEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        binding.nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.registerUserNicknameEditText.getError() != null) {
                    binding.registerUserNicknameEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        binding.surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.registerUserSurnameEditText.getError() != null) {
                    binding.registerUserSurnameEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}