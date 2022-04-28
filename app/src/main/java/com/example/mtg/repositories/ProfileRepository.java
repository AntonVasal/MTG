package com.example.mtg.repositories;

import android.util.Log;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
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
    private static final String PROFILE_REPO = "Profile repo";
    private static final String FAILED = "Failed";


    public ProfileRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    }

    public void getUserData(UserRepositoryCallback userRepositoryCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(PROFILE_REPO, FAILED);
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


//    public MutableLiveData<UserRegisterProfileModel> updateUserName(String name){
//        firebaseFirestore.collection(USERS).document(id)
//                .update(NAME,name).addOnCompleteListener(task -> {
//                   if (task.isSuccessful()){
//                       userRegisterProfileModel = userLiveData.getValue();
//                       assert userRegisterProfileModel != null;
//                       userRegisterProfileModel.setName(name);
//                       userLiveData.postValue(userRegisterProfileModel);
//                   }else {
//                       Log.e(PROFILE_REPO, FAILED);
//                   }
//                });
//        return userLiveData;
//    }


}
