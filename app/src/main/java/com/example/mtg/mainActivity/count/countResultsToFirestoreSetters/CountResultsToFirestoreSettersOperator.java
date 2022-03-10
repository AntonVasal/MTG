package com.example.mtg.mainActivity.count.countResultsToFirestoreSetters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class CountResultsToFirestoreSettersOperator {
    private final FirebaseFirestore mFirebaseFirestore;
    private final FirebaseAuth mAuth;
    private final int typeNumber, typeTask;

    public CountResultsToFirestoreSettersOperator(FirebaseFirestore mFirebaseFirestore, FirebaseAuth mAuth, int typeNumber, int typeTask) {
        this.mFirebaseFirestore = mFirebaseFirestore;
        this.mAuth = mAuth;
        this.typeNumber = typeNumber;
        this.typeTask = typeTask;
    }

    public void resultsToFirestore(int score, int tasksAmount) {
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        AddResultsToFirestoreSetter addResultsToFirestoreSetter = new AddResultsToFirestoreSetter(mFirebaseFirestore);
        MultiResultsToFirestoreSetter multiResultsToFirestoreSetter = new MultiResultsToFirestoreSetter(mFirebaseFirestore);
        SubResultsToFirestoreSetter subResultsToFirestoreSetter = new SubResultsToFirestoreSetter(mFirebaseFirestore);
        DivResultsToFirestoreSetter divResultsToFirestoreSetter = new DivResultsToFirestoreSetter(mFirebaseFirestore);

        switch (typeTask) {
            case 1:
                switch (typeNumber) {
                    case 1:
                        addResultsToFirestoreSetter.setAddNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        addResultsToFirestoreSetter.setAddIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        addResultsToFirestoreSetter.setAddDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 2:
                switch (typeNumber) {
                    case 1:
                        multiResultsToFirestoreSetter.setMultiNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        multiResultsToFirestoreSetter.setMultiIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        multiResultsToFirestoreSetter.setMultiDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 3:
                switch (typeNumber) {
                    case 1:
                        subResultsToFirestoreSetter.setSubNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        subResultsToFirestoreSetter.setSubIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        subResultsToFirestoreSetter.setSubDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
            case 4:
                switch (typeNumber) {
                    case 1:
                        divResultsToFirestoreSetter.setDivNaturalResults(score, tasksAmount, userID);
                        break;
                    case 2:
                        divResultsToFirestoreSetter.setDivIntegerResults(score, tasksAmount, userID);
                        break;
                    case 3:
                        divResultsToFirestoreSetter.setDivDecimalResults(score, tasksAmount, userID);
                        break;
                }
                break;
        }
    }

}
