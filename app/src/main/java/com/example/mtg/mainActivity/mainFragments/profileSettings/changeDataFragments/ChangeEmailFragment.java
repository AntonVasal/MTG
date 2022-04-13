package com.example.mtg.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ChangeEmailFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";
    private static final String SHARED = "is_need_to_close";
    private static final String IS_NEED_TO_CLOSE = "close";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        setViewData();
    }

    private void setViewData() {
        binding.changeEditText.setHint(R.string.email);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_email_24);
        binding.changeButton.setText(R.string.change_email);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeEmailFragment) {
                navController.popBackStack();
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(binding.forChange.getText()).toString().trim();
            if (email.isEmpty()) {
                binding.changeEditText.setError("Email can not be empty");
                binding.changeEditText.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.changeEditText.setError("Please, provide valid email!");
                binding.changeEditText.requestFocus();
                return;
            }
            new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updateEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            setEmailToMainCollection(email);
                        } else {
                            Log.i(TAG, FAILED);
                        }
                    })).start();
        });
    }

    private void setEmailToMainCollection(String email) {
        String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        firebaseFirestore.collection(USERS)
                .document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert userRegisterProfileModel != null;
                    userRegisterProfileModel.setEmail(email);
                    firebaseFirestore.collection(USERS).document(id)
                            .set(userRegisterProfileModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                    if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.changeEmailFragment) {
                                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor e = sharedPreferences.edit();
                                        e.putBoolean(IS_NEED_TO_CLOSE,true);
                                        e.apply();
                                        requireActivity().runOnUiThread(() -> navController.popBackStack());
                                    }
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}