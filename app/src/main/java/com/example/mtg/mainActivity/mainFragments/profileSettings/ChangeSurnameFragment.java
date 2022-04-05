package com.example.mtg.mainActivity.mainFragments.profileSettings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentChangeDataBinding;
import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ChangeSurnameFragment extends Fragment {
    private FragmentChangeDataBinding binding;
    private NavController navController;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangeDataBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        initListeners();
        setViewData();
    }

    private void setViewData() {
        binding.changeEditText.setHint(R.string.surname);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_group_24);
        binding.changeButton.setText(R.string.change_surname);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> navController.popBackStack());
        binding.changeButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.forChange.getText()).toString().trim().length() == 0) {
                binding.changeEditText.setError("Surname can not be empty");
                binding.changeEditText.requestFocus();
                return;
            }
            String surname = binding.forChange.getText().toString().trim();
            String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            firebaseFirestore.collection("users").document(id)
                    .get().addOnSuccessListener(documentSnapshot -> {
                UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                assert userRegisterProfileModel != null;
                userRegisterProfileModel.setSurname(surname);
                firebaseFirestore.collection("users").document(id)
                        .set(userRegisterProfileModel).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("MainActivity", "Success");
                        navController.popBackStack();
                    } else {
                        Log.i("MainActivity", "Failed");
                    }
                });
            });
        });
    }
}