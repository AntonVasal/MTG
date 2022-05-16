package com.example.mtg.repositories.userRepositories;

import android.net.Uri;

import com.example.mtg.App;
import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserRepositoryCallback;
import com.example.mtg.utility.sharedPreferences.SharedPreferencesHolder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String id;
    UserRegisterProfileModel userRegisterProfileModel;
    ListenerRegistration listenerRegistration;
    SharedPreferencesHolder preferencesHolder;
    StorageReference storageReference;
    private static final String USERS = "users";
    private static final String NAME = "name";
    private static final String NICKNAME = "nickname";
    private static final String COUNTRY = "country";
    private static final String SURNAME = "surname";
    private static final String FAILED = "Failed";
    private static final String IMAGE_URL = "imageUrl";
    private static final String EMAIL = "email";
    private static final String SUCCESS = "Success";
    private static final String UPLOADING_FAILED = "uploading failed";
    private static final String ADD = "add";
    private static final String DIV = "div";
    private static final String MULTI = "multi";
    private static final String SUB = "sub";
    private static final String UPLOADING_FAILED_EVERYWHERE = "uploading failed everywhere";

    public ProfileRepository() {
        preferencesHolder = SharedPreferencesHolder.getInstance(App.instance);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
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
                        userRegisterProfileModel.setImageUrl(preferencesHolder.getData(IMAGE_URL));
                        userRepositoryCallback.userRepoCallback(ErrorHandlingRepositoryData.error(error.getMessage(), userRegisterProfileModel));
                        return;
                    }
                    if (value != null && value.exists()) {
                        userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
                        assert userRegisterProfileModel != null;
                        preferencesHolder.setData(NAME,userRegisterProfileModel.getName());
                        preferencesHolder.setData(NICKNAME,userRegisterProfileModel.getNickname());
                        preferencesHolder.setData(EMAIL,userRegisterProfileModel.getEmail());
                        preferencesHolder.setData(SURNAME,userRegisterProfileModel.getSurname());
                        preferencesHolder.setData(COUNTRY,userRegisterProfileModel.getCountry());
                        preferencesHolder.setData(IMAGE_URL,userRegisterProfileModel.getImageUrl());
                        userRepositoryCallback.userRepoCallback(ErrorHandlingRepositoryData.success(userRegisterProfileModel));
                    }
                })).start();
    }

    public void updateUserName(String name, UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id)
                .update(NAME, name).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        preferencesHolder.setData(NAME, name);
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
                        preferencesHolder.setData(COUNTRY, country);
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
                        preferencesHolder.setData(SURNAME, surname);
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void sendPasswordResetEmail(UpdateProfileCallback updateProfileCallback) {
        new Thread(() -> firebaseAuth.sendPasswordResetEmail(preferencesHolder.getData(EMAIL)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> updateProfileCallback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void reAuthCurrentUser(String password, UpdateProfileCallback callback) {
        new Thread(() -> {
            AuthCredential authCredential = EmailAuthProvider.getCredential(preferencesHolder.getData(EMAIL), password);
            Objects.requireNonNull(firebaseAuth.getCurrentUser()).reauthenticate(authCredential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                        } else {
                            callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                        }
                    }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR));
        }).start();
    }

    public void updatePassword(String password, UpdateProfileCallback callback) {
        new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updatePassword(password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
                    } else {
                        callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
                    }
                }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void removeListener() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    public void updateUserEmail(String email, UserFieldFromRepositoryCallback callback) {
        new Thread(() -> Objects.requireNonNull(firebaseAuth.getCurrentUser()).updateEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                preferencesHolder.setData(EMAIL, email);
                updateUserEmailInDatabase(email, status -> {
                    switch (status) {
                        case SUCCESS:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                            break;
                        case ERROR:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, null));
                            break;
                    }
                });
            } else {
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, null));
            }
        }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, null)))).start();
    }

    private void updateUserEmailInDatabase(String email, UpdateProfileCallback callback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id).update(EMAIL, email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void updateUserNickname(String nickname, UserFieldFromRepositoryCallback callback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id).update(NICKNAME, nickname).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                preferencesHolder.setData(NICKNAME, nickname);
                updateUserNicknameInCountLists(nickname, userField -> {
                    switch (userField.status) {
                        case SUCCESS:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                            break;
                        case ERROR:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, userField.data));
                            break;
                    }
                });
            } else {
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, null));
            }
        }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, null)))
        ).start();
    }

    private void updateUserNicknameInCountLists(String nickname, UserFieldFromRepositoryCallback callback) {
        new Thread(() -> {
            AtomicInteger counter = new AtomicInteger();
            Task<Void> uploadTaskSub = firebaseFirestore.collection(SUB).document(id).update(NICKNAME, nickname);
            Task<Void> uploadTaskMulti = firebaseFirestore.collection(MULTI).document(id).update(NICKNAME, nickname);
            Task<Void> uploadTaskDiv = firebaseFirestore.collection(DIV).document(id).update(NICKNAME, nickname);
            firebaseFirestore.collection(ADD).document(id).update(NICKNAME, nickname).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    counter.getAndIncrement();
                }
            }).addOnFailureListener(e -> counter.getAndIncrement())

                    .continueWithTask(task -> uploadTaskSub)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            counter.getAndIncrement();
                        }
                    }).addOnFailureListener(e -> counter.getAndIncrement())

                    .continueWithTask(task -> uploadTaskMulti)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            counter.getAndIncrement();
                        }
                    }).addOnFailureListener(e -> counter.getAndIncrement())
                    .continueWithTask(task -> uploadTaskDiv)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            counter.getAndIncrement();
                        }
                        if (counter.get() == 0) {
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                        } else {
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, counter.toString()));
                        }
                    }).addOnFailureListener(e -> {
                counter.getAndIncrement();
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, counter.toString()));
            });
        }).start();
    }

    public void updateUserProfileImage(Uri imageUri, String imageName, UserFieldFromRepositoryCallback callback) {
        new Thread(() -> {
            StorageReference reference = storageReference.child(imageName);
            UploadTask uploadTask = reference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED_EVERYWHERE, null));
                    return null;
                }
                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    updateUserImageInMainCollection(task.getResult().toString(), userField -> {
                        switch (userField.status) {
                            case SUCCESS:
                                callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                                break;
                            case ERROR:
                                callback.userFieldCallback(ErrorHandlingRepositoryData.error(userField.message, userField.data));
                                break;
                        }
                    });
                } else {
                    callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED_EVERYWHERE, null));
                }
            }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED_EVERYWHERE, null)));
        }).start();
    }

    private void updateUserImageInMainCollection(String imageUrl, UserFieldFromRepositoryCallback callback) {
        new Thread(() -> firebaseFirestore.collection(USERS).document(id).update(IMAGE_URL, imageUrl).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                preferencesHolder.setData(IMAGE_URL, imageUrl);
                uploadUserImageToCountCollection(imageUrl, userField -> {
                    switch (userField.status) {
                        case SUCCESS:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
                            break;
                        case ERROR:
                            callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, userField.data));
                    }
                });
            } else {
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, null));
            }
        }).addOnFailureListener(e -> callback.userFieldCallback(ErrorHandlingRepositoryData.error(UPLOADING_FAILED, null)))).start();
    }

    private void uploadUserImageToCountCollection(String imageUrl, UserFieldFromRepositoryCallback callback) {
        AtomicInteger counter = new AtomicInteger();
        Task<Void> uploadTaskSub = firebaseFirestore.collection(SUB).document(id).update(IMAGE_URL, imageUrl);
        Task<Void> uploadTaskMulti = firebaseFirestore.collection(MULTI).document(id).update(IMAGE_URL, imageUrl);
        Task<Void> uploadTaskDiv = firebaseFirestore.collection(DIV).document(id).update(IMAGE_URL, imageUrl);

        new Thread(() -> firebaseFirestore.collection(ADD).document(id).update(IMAGE_URL, imageUrl).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                counter.getAndIncrement();
            }
        }).addOnFailureListener(e -> counter.getAndIncrement())

                .continueWithTask(task -> uploadTaskSub).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                counter.getAndIncrement();
            }
        }).addOnFailureListener(e -> counter.getAndIncrement())

                .continueWithTask(task -> uploadTaskMulti).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                counter.getAndIncrement();
            }
        }).addOnFailureListener(e -> counter.getAndIncrement())

                .continueWithTask(task -> uploadTaskDiv).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                counter.getAndIncrement();
            }
            if (counter.get() == 0) {
                callback.userFieldCallback(ErrorHandlingRepositoryData.success(SUCCESS));
            } else {
                callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, counter.toString()));
            }
        }).addOnFailureListener(e -> {
            counter.getAndIncrement();
            callback.userFieldCallback(ErrorHandlingRepositoryData.error(FAILED, counter.toString()));
        })).start();
    }

}
