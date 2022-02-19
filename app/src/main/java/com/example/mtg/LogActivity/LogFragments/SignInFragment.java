package com.example.mtg.LogActivity.LogFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mtg.MainActivity.MainActivity;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentSignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class SignInFragment extends Fragment {

   private FragmentSignInBinding binding;
   private FirebaseAuth mAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        initListeners();
        return view;
    }

    private void initListeners() {

        binding.registerTextView.setOnClickListener(view ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.log_activity_container, new RegisterFragment())
                        .commit()
                );

        binding.signInButton.setOnClickListener(view -> userLogin());

        binding.resetPassword.setOnClickListener(view ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.log_activity_container, new ResetPasswordFragment())
                        .commit()
                );
    }



    private void userLogin() {
        String email = Objects.requireNonNull(binding.email.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.password.getText()).toString().trim();

        if(email.isEmpty()){
            binding.signInEmailEditText.setError("Email is required!");
            binding.signInEmailEditText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.signInEmailEditText.setError("Please, provide valid email!");
            binding.signInEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.signInPasswordEditText.setError("Password is required!");
            binding.signInPasswordEditText.requestFocus();
            return;
        }
        if (password.length()<6){
            binding.signInPasswordEditText.setError("Min password length should be 6 characters!");
            binding.signInPasswordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user.isEmailVerified()){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    requireActivity().finish();
                }else {
                    user.sendEmailVerification();
                    Toast.makeText(getContext(),"Please, check your email to verify your account!", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getContext(),"Authentication is failed! Please, try again!", Toast.LENGTH_LONG).show();
            }
        });
    }
}