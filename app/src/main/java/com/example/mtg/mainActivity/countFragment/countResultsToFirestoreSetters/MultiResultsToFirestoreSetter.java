package com.example.mtg.mainActivity.countFragment.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.countFragment.countModels.MultiResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MultiResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";
    private static final String MULTI = "multi";
    private static final String USERS = "users";
    private String nickname;
    private String url;

    public MultiResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }

    public void setMultiDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(MULTI)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            MultiResultsModel multiResultsModel1 = new MultiResultsModel(0, 0, 0, 0, score, tasksAmount,nickname,url, userID);
                            mFirebaseFirestore.collection(MULTI).document(userID)
                                    .set(multiResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiDecimalScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiDecimalScore(score);
                            multiResultsModel.setMultiDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(MULTI)
                                    .document(userID)
                                    .set(multiResultsModel)
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

    public void setMultiIntegerResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(MULTI)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            MultiResultsModel multiResultsModel1 = new MultiResultsModel(0, 0, 0, score, tasksAmount, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(MULTI).document(userID)
                                    .set(multiResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiIntegerScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiIntegerScore(score);
                            multiResultsModel.setMultiIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(MULTI)
                                    .document(userID)
                                    .set(multiResultsModel)
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

    public void setMultiNaturalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection(MULTI)
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        mFirebaseFirestore.collection(USERS).document(userID)
                                .get().addOnSuccessListener(documentSnapshot1 -> {
                            UserRegisterProfileModel userRegisterProfileModel = documentSnapshot1.toObject(UserRegisterProfileModel.class);
                            assert userRegisterProfileModel != null;
                            nickname = userRegisterProfileModel.getNickname();
                            url = userRegisterProfileModel.getImageUrl();
                            MultiResultsModel multiResultsModel1 = new MultiResultsModel(score, tasksAmount, 0, 0, 0, 0,nickname,url, userID);
                            mFirebaseFirestore.collection(MULTI).document(userID)
                                    .set(multiResultsModel1).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, SUCCESS);
                                } else {
                                    Log.i(TAG, FAILED);
                                }
                            });
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiNaturalScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiNaturalScore(score);
                            multiResultsModel.setMultiNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection(MULTI)
                                    .document(userID)
                                    .set(multiResultsModel)
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
