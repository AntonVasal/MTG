package com.example.mtg.LogActivity.LogFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mtg.LogActivity.Models.UserRegisterProfileModel;
import com.example.mtg.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.Objects;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private String userID;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        initListeners();
        return view;
    }

    private void initListeners() {
        binding.registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = Objects.requireNonNull(binding.registerEmailEditText.getEditText()).toString().trim();
        String password = Objects.requireNonNull(binding.registerPasswordEditText.getEditText()).toString().trim();
        String name = Objects.requireNonNull(binding.registerUserNameEditText.getEditText()).toString().trim();
        String surname = Objects.requireNonNull(binding.registerUserSurnameEditText.getEditText()).toString().trim();
        String nickname = Objects.requireNonNull(binding.registerUserNicknameEditText.getEditText()).toString().trim();
        String country = binding.countryPicker.getSelectedCountryName();

        //valid email
        if(email.isEmpty()){
            binding.registerEmailEditText.setError("Email is required!");
            binding.registerEmailEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.registerEmailEditText.setError("Please, provide valid email!");
            binding.registerEmailEditText.requestFocus();
            return;
        }

        //valid password
        if (password.isEmpty()){
            binding.registerPasswordEditText.setError("Password is required");
            binding.registerPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 6){
            binding.registerPasswordEditText.setError("Min password length should be 6 characters!");
            binding.registerPasswordEditText.requestFocus();
            return;
        }

        //valid nickname
        if (nickname.isEmpty()){
            binding.registerUserNicknameEditText.setError("Nickname is required!");
            binding.registerUserNicknameEditText.requestFocus();
            return;
        }

        //valid name
        if (name.isEmpty()){
            binding.registerUserNameEditText.setError("Name is required!");
            binding.registerUserNameEditText.requestFocus();
            return;
        }

        //valid surname
        if (surname.isEmpty()){
            binding.registerUserSurnameEditText.setError("Surname is required!");
            binding.registerUserSurnameEditText.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        UserRegisterProfileModel user = new UserRegisterProfileModel(name, surname, nickname, email, country);

                        mFirebaseFirestore.collection("users").document(userID).set(user).addOnCompleteListener(
                                task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(getContext(),"Data has been already put into database.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getContext(),"Something went wrong. Please try again.",Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                    }else{
                        Toast.makeText(getContext(),"Registration failed! Please, try again!",Toast.LENGTH_LONG).show();
                    }
                });


    }
}