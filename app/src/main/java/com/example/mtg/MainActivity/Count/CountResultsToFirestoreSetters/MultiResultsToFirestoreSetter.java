package com.example.mtg.MainActivity.Count.CountResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.MainActivity.Count.CountModels.MultiResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MultiResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    public MultiResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }

    public void setMultiDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("multi")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        multiResultsModel = new MultiResultsModel(0, 0, 0, 0, score, tasksAmount, userID);
                        mFirebaseFirestore.collection("multi").document(userID)
                                .set(multiResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiDecimalScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiDecimalScore(score);
                            multiResultsModel.setMultiDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("multi")
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
        new Thread(() -> mFirebaseFirestore.collection("multi")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        multiResultsModel = new MultiResultsModel(0, 0, 0, score, tasksAmount, 0, userID);
                        mFirebaseFirestore.collection("multi").document(userID)
                                .set(multiResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiIntegerScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiIntegerScore(score);
                            multiResultsModel.setMultiIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("multi")
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
        new Thread(() -> mFirebaseFirestore.collection("multi")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    MultiResultsModel multiResultsModel = documentSnapshot
                            .toObject(MultiResultsModel.class);
                    if (multiResultsModel == null) {
                        multiResultsModel = new MultiResultsModel(score, tasksAmount, 0, 0, 0, 0, userID);
                        mFirebaseFirestore.collection("multi").document(userID)
                                .set(multiResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = multiResultsModel.getMultiNaturalScore();
                        if (score > oldScore) {
                            multiResultsModel.setMultiNaturalScore(score);
                            multiResultsModel.setMultiNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("multi")
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
