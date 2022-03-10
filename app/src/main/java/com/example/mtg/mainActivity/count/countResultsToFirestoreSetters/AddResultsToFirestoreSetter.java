package com.example.mtg.mainActivity.count.countResultsToFirestoreSetters;

import android.util.Log;

import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddResultsToFirestoreSetter {

    private final FirebaseFirestore mFirebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    public AddResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore) {
        this.mFirebaseFirestore = mFirebaseFirestore;
    }

    public void setAddDecimalResults(int score, int tasksAmount, String userID) {
        new Thread(() -> mFirebaseFirestore.collection("add")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        addResultsModel = new AddResultsModel(0, 0, 0, 0, score, tasksAmount, userID);
                        mFirebaseFirestore.collection("add").document(userID)
                                .set(addResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = addResultsModel.getAddDecimalScore();
                        if (score > oldScore) {
                            addResultsModel.setAddDecimalScore(score);
                            addResultsModel.setAddDecimalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("add")
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
        new Thread(() -> mFirebaseFirestore.collection("add")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        addResultsModel = new AddResultsModel(0, 0, score, tasksAmount, 0, 0, userID);
                        mFirebaseFirestore.collection("add").document(userID)
                                .set(addResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = addResultsModel.getAddIntegerScore();
                        if (score > oldScore) {
                            addResultsModel.setAddIntegerScore(score);
                            addResultsModel.setAddIntegerTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("add")
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
        new Thread(() -> mFirebaseFirestore.collection("add")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    AddResultsModel addResultsModel = documentSnapshot
                            .toObject(AddResultsModel.class);
                    if (addResultsModel == null) {
                        addResultsModel = new AddResultsModel(score, tasksAmount, 0, 0, 0, 0, userID);
                        mFirebaseFirestore.collection("add").document(userID)
                                .set(addResultsModel).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i(TAG, SUCCESS);
                            } else {
                                Log.i(TAG, FAILED);
                            }
                        });
                    } else {
                        int oldScore = addResultsModel.getAddNaturalScore();
                        if (score > oldScore) {
                            addResultsModel.setAddNaturalScore(score);
                            addResultsModel.setAddNaturalTasksAmount(tasksAmount);
                            mFirebaseFirestore.collection("add")
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
