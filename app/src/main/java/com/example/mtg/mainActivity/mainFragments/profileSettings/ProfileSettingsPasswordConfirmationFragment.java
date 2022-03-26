package com.example.mtg.mainActivity.mainFragments.profileSettings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentProfileSettingsPasswordConfirmationBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileSettingsPasswordConfirmationFragment extends Fragment {

    FragmentProfileSettingsPasswordConfirmationBinding binding;
    String password;
    String email;
    NavController navController;

    int typeFragments;

    public static ProfileSettingsPasswordConfirmationFragment newInstance(int typeFragments){
        Bundle args = new Bundle();
        args.putInt("typeFragments", typeFragments);
        ProfileSettingsPasswordConfirmationFragment f = new ProfileSettingsPasswordConfirmationFragment();
        f.setArguments(args);
        return f;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        typeFragments = getArguments().getInt("typeFragments");
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
            assert userRegisterProfileModel != null;
            email = userRegisterProfileModel.getEmail();
        });
        initListeners();
    }

    private void initListeners() {
        binding.confirmPasswordBackButton.setOnClickListener(view -> navController.popBackStack());
        binding.confirmPasswordButton.setOnClickListener(view -> {

            password = Objects.requireNonNull(binding.passwordForReset.getText()).toString().trim();

            if (password.isEmpty()){
                binding.confirmPasswordEditText.setError("Password is required");
                binding.confirmPasswordEditText.requestFocus();
                return;
            }

            if (password.length() < 6){
                binding.confirmPasswordEditText.setError("Min password length should be 6 characters!");
                binding.confirmPasswordEditText.requestFocus();
                return;
            }

            AuthCredential authCredential = EmailAuthProvider.getCredential(email,password);
            Objects.requireNonNull(FirebaseAuth.getInstance()
                    .getCurrentUser())
                    .reauthenticate(authCredential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            NavDirections navDirections;
                            switch (typeFragments){
                                case 1:
                                    navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                            .actionProfileSettingsPasswordConfirmationFragmentToChangeNicknameFragment();
                                    navController.navigate(navDirections);
                                    break;
                                case 2:
                                    navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                            .actionProfileSettingsPasswordConfirmationFragmentToChangeNameFragment();
                                    navController.navigate(navDirections);
                                    break;
                                case 3:
                                    navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                            .actionProfileSettingsPasswordConfirmationFragmentToChangeSurnameFragment();
                                    navController.navigate(navDirections);
                                    break;
                                case 4:
                                    navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                            .actionProfileSettingsPasswordConfirmationFragmentToChangeEmailFragment();
                                    navController.navigate(navDirections);
                                    break;
                                case 5:
                                    navDirections = ProfileSettingsPasswordConfirmationFragmentDirections
                                            .actionProfileSettingsPasswordConfirmationFragmentToChangeCountryFragment();
                                    navController.navigate(navDirections);
                                    break;
                            }
                        }else{
                            Log.i("MainActivity","Failed");
                            Toast.makeText(requireActivity(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    });

        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingsPasswordConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}