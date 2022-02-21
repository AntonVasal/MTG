package com.example.mtg.LogActivity.LogFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentResetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        initListeners();
        textChanged();
        return view;
    }

    private void textChanged() {
        binding.emailForReset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.resetEmailEditText.setError(null);
                binding.resetEmailEditText.clearFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }


    private void initListeners() {
        binding.resetPasswordButton.setOnClickListener(view ->
                    resetPassword()
                );

        binding.resetBackButton.setOnClickListener(view ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.log_activity_container,new SignInFragment())
                        .commit()
                );
    }

    private void resetPassword() {
        String email = Objects.requireNonNull(binding.emailForReset.getText()).toString().trim();

        if (email.isEmpty()){
            binding.resetEmailEditText.setError("Email is required!");
            binding.resetEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.resetEmailEditText.setError("Please, provide valid email!");
            binding.resetEmailEditText.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getContext(),"Please, check your email to reset password!",Toast.LENGTH_LONG)
                        .show();
            }else{
                Toast.makeText(getContext(),"Something went wrong. Please, try again!", Toast.LENGTH_LONG)
                        .show();
            }
        });


    }
}