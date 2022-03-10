package com.example.mtg.mainActivity.count.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class DivResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    public DivResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }
    public void setDivDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("div")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        divResultsModel = new DivResultsModel(0, 0, 0, 0, score, tasksAmount, userID);
                        mFirebaseFirestore.collection("div").document(userID)
                                .set(divResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = divResultsModel.getDivDecimalScore();
                        if (score > oldScore) {
                            divResultsModel.setDivDecimalScore(score);
                            divResultsModel.setDivDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("div")
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
        new Thread(() -> mFirebaseFirestore.collection("div")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        divResultsModel = new DivResultsModel(0, 0, score, tasksAmount, 0, 0, userID);
                        mFirebaseFirestore.collection("div").document(userID)
                                .set(divResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = divResultsModel.getDivIntegerScore();
                        if (score > oldScore) {
                            divResultsModel.setDivIntegerScore(score);
                            divResultsModel.setDivIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("div")
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
        new Thread(() -> mFirebaseFirestore.collection("div")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    DivResultsModel divResultsModel = documentSnapshot
                            .toObject(DivResultsModel.class);
                    if (divResultsModel == null) {
                        divResultsModel = new DivResultsModel(score, tasksAmount, 0, 0, 0, 0, userID);
                        mFirebaseFirestore.collection("div").document(userID)
                                .set(divResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = divResultsModel.getDivNaturalScore();
                        if (score > oldScore) {
                            divResultsModel.setDivNaturalScore(score);
                            divResultsModel.setDivNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("div")
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
