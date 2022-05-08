package com.example.mtg.repositories;

import com.example.mtg.models.countModels.MultiResultsModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.ArraysFromRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String MULTI = "multi";
    private static final String FAILED = "Failed";

    public MultiRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loadMultiCollection(ArraysFromRepositoryCallback<MultiResultsModel> callback) {
        ArrayList<MultiResultsModel> arrayList = new ArrayList<>();
        new Thread(() -> firebaseFirestore.collection(MULTI).addSnapshotListener((value, error) -> {
            if (error != null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(error.getMessage(), null));
                return;
            }
            if (value == null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(FAILED, null));
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                MultiResultsModel multiResultsModel = dc.getDocument().toObject(MultiResultsModel.class);
                switch (dc.getType()) {
                    case ADDED:
                        arrayList.add(multiResultsModel);
                        break;
                    case MODIFIED:
                        int k = 0;
                        String id = multiResultsModel.getId();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId().equals(id)) {
                                k = i;
                            }
                        }
                        arrayList.set(k, multiResultsModel);
                        break;
                }
                callback.arrayFromRepository(ErrorHandlingRepositoryData.success(arrayList));
            }
        })).start();
    }
}
