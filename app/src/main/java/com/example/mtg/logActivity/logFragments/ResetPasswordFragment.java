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
import com.example.mtg.databinding.FragmentResetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
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
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        textChanged();
    }

    private void textChanged() {
        binding.emailForReset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.resetEmailEditText.getError()!=null){
                    binding.resetEmailEditText.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void initListeners() {
        binding.resetPasswordButton.setOnClickListener(view -> resetPassword());

        binding.resetBackButton.setOnClickListener(view -> navController.popBackStack());
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(binding.emailForReset.getText()).toString().trim();

        if (email.isEmpty()){
            binding.resetEmailEditText.setError(getResources().getString(R.string.email_is_required));
            binding.resetEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.resetEmailEditText.setError(getResources().getString(R.string.pls_provide_valid_email));
            binding.resetEmailEditText.requestFocus();
            return;
        }

        new Thread(() -> mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getContext(),"Please, check your email to reset password!",Toast.LENGTH_LONG)
                        .show();
            }else{
                Toast.makeText(getContext(),"Something went wrong. Please, try again!", Toast.LENGTH_LONG)
                        .show();
            }
        })).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}