package com.example.mtg.mainActivity.count.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class SubResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    public SubResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }
    public void setSubDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("sub")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    SubResultsModel subResultsModel = documentSnapshot
                            .toObject(SubResultsModel.class);
                    if (subResultsModel == null) {
                        subResultsModel = new SubResultsModel(0, 0, 0, 0, score, tasksAmount, userID);
                        mFirebaseFirestore.collection("sub").document(userID)
                                .set(subResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = subResultsModel.getSubDecimalScore();
                        if (score > oldScore) {
                            subResultsModel.setSubDecimalScore(score);
                            subResultsModel.setSubDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("sub")
                                    .document(userID)
                                    .set(subResultsModel)
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

    public void setSubIntegerResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("sub")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    SubResultsModel subResultsModel = documentSnapshot
                            .toObject(SubResultsModel.class);
                    if (subResultsModel == null) {
                        subResultsModel = new SubResultsModel(0, 0, score, tasksAmount, 0, 0, userID);
                        mFirebaseFirestore.collection("sub").document(userID)
                                .set(subResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = subResultsModel.getSubIntegerScore();
                        if (score > oldScore) {
                            subResultsModel.setSubIntegerScore(score);
                            subResultsModel.setSubIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("sub")
                                    .document(userID)
                                    .set(subResultsModel)
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

    public void setSubNaturalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("sub")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    SubResultsModel subResultsModel = documentSnapshot
                            .toObject(SubResultsModel.class);
                    if (subResultsModel == null) {
                        subResultsModel = new SubResultsModel(score, tasksAmount, 0, 0, 0, 0, userID);
                        mFirebaseFirestore.collection("sub").document(userID)
                                .set(subResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = subResultsModel.getSubNaturalScore();
                        if (score > oldScore) {
                            subResultsModel.setSubNaturalScore(score);
                            subResultsModel.setSubNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("sub")
                                    .document(userID)
                                    .set(subResultsModel)
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
