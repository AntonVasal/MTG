package com.example.mtg.ui.activities.mainActivity.countFragment.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.models.countModels.DivResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DivResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String DIV = "div";
    private static final String USERS = "users";
    private String nickname;
    private String url;

    public DivResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }

    public void setDivDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(DIV)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            DivResultsModel divResultsModel1 = new DivResultsModel(0, 0, 0, 0, score, tasksAmount,nickname,url, userID);
                            mFirebaseFirestore.collection(DIV).document(userID)
                                    .set(divResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = divResultsModel.getDivDecimalScore();
                        if (score > oldScore) {
                            divResultsModel.setDivDecimalScore(score);
                            divResultsModel.setDivDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(DIV)
                                    .document(userID)
                                    .set(divResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })
        ).start();
    }

    public void setDivIntegerResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(DIV)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            DivResultsModel divResultsModel1 = new DivResultsModel(0, 0, score, tasksAmount, 0, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(DIV).document(userID)
                                    .set(divResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = divResultsModel.getDivIntegerScore();
                        if (score > oldScore) {
                            divResultsModel.setDivIntegerScore(score);
                            divResultsModel.setDivIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(DIV)
                                    .document(userID)
                                    .set(divResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })
        ).start();
    }

    public void setDivNaturalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(DIV)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            DivResultsModel divResultsModel1 = new DivResultsModel(score, tasksAmount, 0, 0, 0, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(DIV).document(userID)
                                    .set(divResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = divResultsModel.getDivNaturalScore();
                        if (score > oldScore) {
                            divResultsModel.setDivNaturalScore(score);
                            divResultsModel.setDivNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(DIV)
                                    .document(userID)
                                    .set(divResultsModel)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.i(TAG, SUCCESS);
                                        } else {
                                            Log.i(TAG, FAILED);
                                        }
                                    });
                        }
                    }
                })
        ).start();
    }
}
