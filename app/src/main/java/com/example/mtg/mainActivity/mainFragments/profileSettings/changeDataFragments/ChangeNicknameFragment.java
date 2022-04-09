package com.example.mtg.mainActivity.mainFragments.profileSettings.changeDataFragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.example.mtg.mainActivity.count.countModels.MultiResultsModel;
import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ChangeNicknameFragment extends Fragment {

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
        binding = FragmentChangeDataBinding.inflate(inflater, container, false);
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
        binding.changeEditText.setHint(R.string.nickname);
        binding.changeEditText.setStartIconDrawable(R.drawable.ic_baseline_star_24);
        binding.changeButton.setText(R.string.change_nickname);
    }

    private void initListeners() {
        binding.changeBackButton.setOnClickListener(view -> navController.popBackStack());

        binding.changeButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(binding.forChange.getText()).toString().trim().isEmpty()) {
                binding.changeEditText.setError("Nickname can not be empty");
                binding.changeEditText.requestFocus();
                return;
            }
            String nickname = binding.forChange.getText().toString().trim();
            String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            sendNicknameToFirestore(nickname, id);
        });

    }

    private void sendNicknameToFirestore(String nickname, String id) {
        firebaseFirestore.collection("users").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert userRegisterProfileModel != null;
                    userRegisterProfileModel.setNickname(nickname);
                    firebaseFirestore.collection("users").document(id).set(userRegisterProfileModel).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            sendNicknameToCountFirestore(nickname, id);
                        } else {
                            Log.i("MainActivity", "Failed");
                        }
                    });
                });
    }

    private void sendNicknameToCountFirestore(String nickname, String id) {
        firebaseFirestore.collection("add").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot.toObject(AddResultsModel.class);
                    assert addResultsModel != null;
                    addResultsModel.setNickname(nickname);
                    firebaseFirestore.collection("add").document(id)
                            .set(addResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i("MainActivity", "Success");
                                } else {
                                    Log.i("MainActivity", "Failed");
                                }
                            });
                });
        firebaseFirestore.collection("div").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot.toObject(DivResultsModel.class);
                    assert divResultsModel != null;
                    divResultsModel.setNickname(nickname);
                    firebaseFirestore.collection("div").document(id)
                            .set(divResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i("MainActivity", "Success");
                                } else {
                                    Log.i("MainActivity", "Failed");
                                }
                            });
                });
        firebaseFirestore.collection("sub").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    SubResultsModel subResultsModel = documentSnapshot.toObject(SubResultsModel.class);
                    assert subResultsModel != null;
                    subResultsModel.setNickname(nickname);
                    firebaseFirestore.collection("sub").document(id)
                            .set(subResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i("MainActivity", "Success");
                                } else {
                                    Log.i("MainActivity", "Failed");
                                }
                            });
                });
        firebaseFirestore.collection("multi").document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot.toObject(MultiResultsModel.class);
                    assert multiResultsModel != null;
                    multiResultsModel.setNickname(nickname);
                    firebaseFirestore.collection("multi").document(id)
                            .set(multiResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i("MainActivity", "Success");
                                    navController.popBackStack();
                                } else {
                                    Log.i("MainActivity", "Failed");
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