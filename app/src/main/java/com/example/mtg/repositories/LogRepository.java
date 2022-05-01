package com.example.mtg.repositories;

import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public LogRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void sendResetPasswordEmail(String email, UpdateProfileCallback callback) {
        new Thread(() -> firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR))).start();
    }

    public void signInUser(String email, String password, UpdateProfileCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.SUCCESS);
            } else {
                callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR);
            }
        }).addOnFailureListener(e -> callback.updateProfileCallback(ErrorHandlingRepositoryData.Status.ERROR));
    }
}
