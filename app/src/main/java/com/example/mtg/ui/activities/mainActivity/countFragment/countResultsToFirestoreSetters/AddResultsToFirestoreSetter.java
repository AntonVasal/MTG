package com.example.mtg.ui.activities.mainActivity.countFragment.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.models.countModels.AddResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String ADD = "add";
    private static final String USERS = "users";
    private String nickname;
    private String url;

    public AddResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }

    public void setAddDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(ADD)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            AddResultsModel addResultsModel1 = new AddResultsModel(0, 0, 0, 0, score, tasksAmount,nickname,url,userID);
                            mFirebaseFirestore.collection(ADD).document(userID)
                                    .set(addResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = addResultsModel.getAddDecimalScore();
                        if (score > oldScore) {
                            addResultsModel.setAddDecimalScore(score);
                            addResultsModel.setAddDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(ADD)
                                    .document(userID)
                                    .set(addResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })).start();
    }

    public void setAddIntegerResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(ADD)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            AddResultsModel addResultsModel1 = new AddResultsModel(0, 0, score, tasksAmount, 0, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(ADD).document(userID)
                                    .set(addResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = addResultsModel.getAddIntegerScore();
                        if (score > oldScore) {
                            addResultsModel.setAddIntegerScore(score);
                            addResultsModel.setAddIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(ADD)
                                    .document(userID)
                                    .set(addResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })).start();
    }

    public void setAddNaturalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(ADD)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            AddResultsModel addResultsModel1 = new AddResultsModel(score, tasksAmount, 0, 0, 0, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(ADD).document(userID)
                                    .set(addResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = addResultsModel.getAddNaturalScore();
                        if (score > oldScore) {
                            addResultsModel.setAddNaturalScore(score);
                            addResultsModel.setAddNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(ADD)
                                    .document(userID)
                                    .set(addResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })).start();
    }


}
