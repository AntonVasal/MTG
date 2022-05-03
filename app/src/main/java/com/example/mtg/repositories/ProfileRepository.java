package com.example.mtg.repositories;

import android.util.Log;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserRepositoryCallback;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Objects;

public class ProfileRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String id;
    UserRegisterProfileModel userRegisterProfileModel;
    ListenerRegistration listenerRegistration;
    SharedPreferencesHolder preferencesHolder;
    private static final String USERS = "users";
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";
    private static final String COUNTRY = "country";
    private static final String SURNAME = "surname";
    private static final String FAILED = "Failed";
    private static final String EMAIL = "email";
    private static final String SUCCESS = "Success";
    private static final String UPLOADING_FAILED = "uploading failed";

    public ProfileRepository() {
        preferencesHolder = SharedPreferencesHolder.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        id = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    }

    public void getUserData(UserRepositoryCallback userRepositoryCallback) {
        new Thread(() -> listenerRegistration = firebaseFirestore.collection(USERS).document(id)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        userRegisterProfileModel.setName(preferencesHolder.getData(NAME));
                        userRegisterProfileModel.setNickname(preferencesHolder.getData(NICKNAME));
                        userRegisterProfileModel.setCountry(preferencesHolder.getData(COUNTRY));
                        userRegisterProfileModel.setSurname(preferencesHolder.getData(SURNAME));
                        userRegisterProfileModel.setEmail(preferencesHolder.getData(EMAIL));
                        userRepositoryCallback.userRepoCallback(ErrorHandlingRepositoryData.error(error.getMessage(), userRegisterProfileModel));
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
                        preferencesHolder.setData(NAME,name);
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void updateUserCountry(String country, UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .update(COUNTRY, country).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        preferencesHolder.setData(COUNTRY,country);
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void updateUserSurname(String surname, UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .update(SURNAME, surname).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        preferencesHolder.setData(SURNAME,surname);
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

//    private void getUserEmail(UserFieldFromRepositoryCallback callback) {
//        preferencesHolder.getData(EMAIL);
//        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
//                .get().addOnSuccessListener(documentSnapshot -> {
//                    userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
//                    assert userRegisterProfileModel != null;
//                    callback.userFieldCallback(ErrorHandlingRepositoryData.success(userRegisterProfileModel.getEmail()));
//                }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(e.getMessage(), null)))).start();
//    }

    public void sendPasswordResetEmail(UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> {
//                getUserEmail(userField -> {
//            switch (userField.status) {
//                case SUCCESS:
//                    assert userField.data != null;
                    firebaseAuth.sendPasswordResetEmail(preferencesHolder.getData(EMAIL)).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                        } else {
                            updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                        }
                    }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR));
//                    break;
//                case ERROR:
//                    updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
//                    break;
//            }
        }
//        )
        ).start();
    }

    public void reAuthCurrentUser(String password, UpdateProfileCallback callback) {
        new Thread(() -> {
//                getUserEmail(userField -> {
//            switch (userField.status) {
//                case SUCCESS:
//                    assert userField.data != null;
                    AuthCredential authCredential = EmailAuthProvider.getCredential(preferencesHolder.getData(EMAIL), password);
                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).reauthenticate(authCredential)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                                } else {
                                    callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                                }
                            }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR));
//                    break;
//                case ERROR:
//                    callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
//                    break;
//            }
        }
//        )
        ).start();
    }

    public void updatePassword(String password, UpdateProfileCallback callback){
        new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updatePassword(password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    }else{
                        callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void removeListener(){
        if (listenerRegistration != null){
            listenerRegistration.remove();
            Log.e("Listener","Remove");
        }
    }

    public void updateUserEmail(String email, UserFieldFromRepositoryCallback callback){
        new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updateEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                preferencesHolder.setData(EMAIL,email);
                updateUserEmailInDatabase(email, status -> {
                    switch (status){
                        case SUCCESS:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                            break;
                        case ERROR:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED,null));
                            break;
                    }
                });
            }else{
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED,null));
            }
        }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED,null)))).start();
    }

    private void updateUserEmailInDatabase(String email, UpdateProfileCallback callback){
        new Thread(() -> firebaseFirestore.collection(USERS).document(id).update(EMAIL,email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            }else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }


}
