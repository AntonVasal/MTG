package com.example.mtg.repositories;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LogRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    SharedPreferencesHolder preferencesHolder;
    private static final String USERS = "users";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";
    private static final String SURNAME = "surname";
    private static final String COUNTRY = "country";
    private static final String FAILED_TO_MAKE_USER = "Failed to create user";
    private static final String SUCCESS_CREATING_USER = "User was successfully created and data was pushed to database";
    private static final String DATA_NOT_PUSHED = "Data was not pushed to database";

    public LogRepository() {
        preferencesHolder = SharedPreferencesHolder.getInstance();
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
                preferencesHolder.setData(EMAIL,email);
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

                preferencesHolder.setData(NAME,user.getName());
                preferencesHolder.setData(NICKNAME,user.getNickname());
                preferencesHolder.setData(EMAIL,user.getEmail());
                preferencesHolder.setData(SURNAME,user.getSurname());
                preferencesHolder.setData(COUNTRY,user.getCountry());

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
