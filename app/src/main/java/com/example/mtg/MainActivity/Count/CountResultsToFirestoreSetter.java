package com.example.mtg.MainActivity.Count;

import android.util.Log;

import com.example.mtg.MainActivity.Count.CountModels.AddResultsModel;
import com.example.mtg.MainActivity.Count.CountModels.MultiResultsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class CountResultsToFirestoreSetter {
    private final FirebaseFirestore mFirebaseFirestore;
    private final FirebaseAuth mAuth;
    private final int typeNumber, typeTask;
    private static final String TAG = "MainActivity";
    private static final String SUCCESS = "Success";
    private static final String FAILED = "Failed";

    public CountResultsToFirestoreSetter(FirebaseFirestore mFirebaseFirestore, FirebaseAuth mAuth, int typeNumber, int typeTask) {
        this.mFirebaseFirestore = mFirebaseFirestore;
        this.mAuth = mAuth;
        this.typeNumber = typeNumber;
        this.typeTask = typeTask;
    }

    public void resultsToFirestore(int score, int tasksAmount) {
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        switch (typeTask) {
            case 1:
                switch (typeNumber) {
                    case 1:
                        setAddNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        setAddIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        setAddDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 2:
                switch (typeNumber) {
                    case 1:
                        setMultiNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        setMultiIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        setMultiDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 3:
                switch (typeNumber) {
                    case 1:
                        setSubNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        setSubIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        setSubDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 4:
                switch (typeNumber) {
                    case 1:
                        setDivNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        setDivIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        setDivDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
        }
    }

    private void setDivDecimalResults(int score, int tasksAmount, String userID) {

    }

    private void setDivIntegerResults(int score, int tasksAmount, String userID) {
    }

    private void setDivNaturalResults(int score, int tasksAmount, String userID) {
    }

    private void setSubDecimalResults(int score, int tasksAmount, String userID) {
    }

    private void setSubIntegerResults(int score, int tasksAmount, String userID) {
    }

    private void setSubNaturalResults(int score, int tasksAmount, String userID) {
    }

    private void setMultiDecimalResults(int score, int tasksAmount, String userID) {
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

    private void setMultiIntegerResults(int score, int tasksAmount, String userID) {
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

    private void setMultiNaturalResults(int score, int tasksAmount, String userID) {
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

    private void setAddDecimalResults(int score, int tasksAmount, String userID) {
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

    private void setAddIntegerResults(int score, int tasksAmount, String userID) {
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

    private void setAddNaturalResults(int score, int tasksAmount, String userID) {
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
