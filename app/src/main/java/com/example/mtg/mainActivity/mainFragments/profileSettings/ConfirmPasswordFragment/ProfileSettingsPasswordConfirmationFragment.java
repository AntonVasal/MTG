package com.example.mtg.mainActivity.mainFragments.profileSettings.ConfirmPasswordFragment;

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

    private FragmentProfileSettingsPasswordConfirmationBinding binding;
    private String password;
    private String email;
    private NavController navController;
    private static final String TAG = "MainActivity";
    private static final String TYPE_FRAGMENTS = "typeFragments";
    private static final String USERS = "users";
    private static final String FAILED = "Failed";

    private int typeFragments;

    public static ProfileSettingsPasswordConfirmationFragment newInstance(int typeFragments){
        Bundle args = new Bundle();
        args.putInt(TYPE_FRAGMENTS, typeFragments);
        ProfileSettingsPasswordConfirmationFragment f = new ProfileSettingsPasswordConfirmationFragment();
        f.setArguments(args);
        return f;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        typeFragments = getArguments().getInt(TYPE_FRAGMENTS);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        new Thread(() -> firebaseFirestore.collection(USERS).document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get().addOnSuccessListener(documentSnapshot -> {
            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
            assert userRegisterProfileModel != null;
            email = userRegisterProfileModel.getEmail();
        })).start();
        initListeners();
    }

    private void initListeners() {
        binding.confirmPasswordBackButton.setOnClickListener(view -> navController.popBackStack());
        binding.confirmPasswordButton.setOnClickListener(view -> {

            password = Objects.requireNonNull(binding.passwordForConfirm.getText()).toString().trim();

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

            new Thread(() -> {
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
                                Log.i(TAG,FAILED);
                                Toast.makeText(requireActivity(),FAILED,Toast.LENGTH_LONG).show();
                            }
                        });
            }).start();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileSettingsPasswordConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}