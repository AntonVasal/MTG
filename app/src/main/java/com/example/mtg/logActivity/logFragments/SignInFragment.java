package com.example.mtg.logActivity.logFragments;

import android.content.Intent;
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

import com.example.mtg.mainActivity.MainActivity;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentSignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private FirebaseAuth mAuth;
    private NavController navController;


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
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        textSelected();
    }

    private void textSelected() {
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.signInPasswordEditText.getError()!=null){
                    binding.signInPasswordEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.signInEmailEditText.getError()!=null){
                    binding.signInEmailEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
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

        new Thread(() -> mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                if (user.isEmailVerified()) {
//                    if (Objects.requireNonNull(navController.getCurrentDestination()).getId()==R.id.signInFragment){
                    requireActivity().runOnUiThread(() -> {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        requireActivity().finish();
                    });
//                    }
                } else {
                    user.sendEmailVerification();
                    Toast.makeText(getContext(), "Please, check your email to verify your account!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getContext(), "Authentication is failed! Please, try again!", Toast.LENGTH_LONG).show();
            }
        })).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}