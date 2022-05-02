package com.example.mtg.repositories;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LogRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String USERS = "users";
    private static final String FAILED_TO_MAKE_USER = "Failed to create user";
    private static final String SUCCESS_CREATING_USER = "User was successfully created and data was pushed to database";
    private static final String DATA_NOT_PUSHED = "Data was not pushed to database";

    public LogRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void sendResetPasswordEmail(String email, UpdateProfileCallback callback) {
        new Thread(() -> firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void signInUser(String email, String password, UpdateProfileCallback callback) {
        new Thread(() -> firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void createNewUser(UserRegisterProfileModel user, String password, UserFieldFromRepositoryCallback callback){
        new Thread(() -> firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                String id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                setUserDataToFirestore(id, user, status -> {
                    switch (status){
                        case SUCCESS:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS_CREATING_USER));
                            break;
                        case ERROR:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(DATA_NOT_PUSHED,null));
                            break;
                    }
                });
            }else{
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED_TO_MAKE_USER,null));
            }
        }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED_TO_MAKE_USER,null)))).start();
    }

    private void setUserDataToFirestore(String id,UserRegisterProfileModel user, UpdateProfileCallback updateProfileCallback) {
       new Thread(() -> firebaseFirestore.collection(USERS).document(id).set(user).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
           }else {
               updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
           }
       }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }
}
