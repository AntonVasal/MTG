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
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";
    private static final String ADD = "add";
    private static final String DIV = "div";
    private static final String MULTI = "multi";
    private static final String SUB = "sub";

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
        firebaseFirestore.collection(USERS).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                    assert userRegisterProfileModel != null;
                    userRegisterProfileModel.setNickname(nickname);
                    firebaseFirestore.collection(USERS).document(id).set(userRegisterProfileModel).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            new Thread(() -> sendNicknameToCountFirestore(nickname, id)).start();
                        } else {
                            Log.i(TAG, FAILED);
                        }
                    });
                });
    }

    private void sendNicknameToCountFirestore(String nickname, String id) {
        firebaseFirestore.collection(ADD).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot.toObject(AddResultsModel.class);
                    assert addResultsModel != null;
                    addResultsModel.setNickname(nickname);
                    firebaseFirestore.collection(ADD).document(id)
                            .set(addResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                });
        firebaseFirestore.collection(DIV).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot.toObject(DivResultsModel.class);
                    assert divResultsModel != null;
                    divResultsModel.setNickname(nickname);
                    firebaseFirestore.collection(DIV).document(id)
                            .set(divResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                });
        firebaseFirestore.collection(SUB).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    SubResultsModel subResultsModel = documentSnapshot.toObject(SubResultsModel.class);
                    assert subResultsModel != null;
                    subResultsModel.setNickname(nickname);
                    firebaseFirestore.collection(SUB).document(id)
                            .set(subResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                });
        firebaseFirestore.collection(MULTI).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot.toObject(MultiResultsModel.class);
                    assert multiResultsModel != null;
                    multiResultsModel.setNickname(nickname);
                    firebaseFirestore.collection(MULTI).document(id)
                            .set(multiResultsModel)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                    navController.popBackStack();
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