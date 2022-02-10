package com.example.mtg.LogActivity.LogFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mtg.LogActivity.LogActivity;
import com.example.mtg.MainActivity.MainActivity;
import com.example.mtg.R;
import com.example.mtg.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.log_activity_container, new RegisterFragment())
                        .addToBackStack("")
                        .commit()
                );

        binding.signInButton.setOnClickListener(view -> userLogin());
    }

    private void userLogin() {
        String email = Objects.requireNonNull(binding.signInEmailEditText.getEditText()).toString().trim();
        String password = Objects.requireNonNull(binding.signInPasswordEditText.getEditText()).toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()){
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(getContext(),"Please, check your email to verify your account!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(),"Authentication if failed! Please, try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}