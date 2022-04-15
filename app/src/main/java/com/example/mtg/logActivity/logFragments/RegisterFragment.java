package com.example.mtg.logActivity.logFragments;

import android.os.Bundle;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.log_fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
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

                        UserRegisterProfileModel user = new UserRegisterProfileModel(name, surname, nickname, email, country,"no image");

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
                        if (navController.getCurrentDestination().getId()==R.id.registerFragment){
                            navController.popBackStack();
                        }
//                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
//                                task12 -> {
//                                    if (task12.isSuccessful()){
//
//                                            startActivity(new Intent(getActivity(), MainActivity.class));
//                                            requireActivity().finish();
//
//                                    } else {
//                                        Toast.makeText(getContext(),"Auto-authentication is failed! Please, try again!", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//
//                        );

                    }else{
                        Toast.makeText(getContext(),"Registration failed! Please, try again!",Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void textSelected() {
        binding.email.setOnClickListener(view -> {
            binding.registerEmailEditText.setError(null);
            binding.registerEmailEditText.clearFocus();
        });
        binding.password.setOnClickListener(view -> {
            binding.registerPasswordEditText.setError(null);
            binding.registerPasswordEditText.clearFocus();
        });
        binding.name.setOnClickListener(view -> {
            binding.registerUserNameEditText.setError(null);
            binding.registerUserNameEditText.clearFocus();
        });
        binding.nickname.setOnClickListener(view -> {
            binding.registerUserNicknameEditText.setError(null);
            binding.registerUserNicknameEditText.clearFocus();
        });
        binding.surname.setOnClickListener(view -> {
            binding.registerUserSurnameEditText.setError(null);
            binding.registerUserSurnameEditText.clearFocus();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}