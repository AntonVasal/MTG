package com.example.mtg.repositories;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String id;
    UserRegisterProfileModel userRegisterProfileModel;
    private static final String USERS = "users";
    private static final String NAME = "name";
    private static final String COUNTRY = "country";


    public ProfileRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    }

    public void getUserData(UserRepositoryCallback userRepositoryCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        userRepositoryCallback.userRepoCallback(ErrorHandlingRepositoryData.error(error.getMessage(), null));
                        return;
                    }
                    if (value != null && value.exists()) {
                        userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        userRepositoryCallback.userRepoCallback(ErrorHandlingRepositoryData.success(userRegisterProfileModel));
                    }
                })).start();
    }


    public void updateUserName(String name, UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .update(NAME, name).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                })).start();
    }

    public void updateUserCountry(String country, UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .update(COUNTRY, country).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                })).start();
    }

}
