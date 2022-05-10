package com.example.mtg.repositories.countRepositories;

import com.example.mtg.models.countModels.SubResultsModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.ArraysFromRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String SUB = "sub";
    private static final String FAILED = "Failed";

    public SubRepository(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loadSubCollection(ArraysFromRepositoryCallback<SubResultsModel> callback){
        ArrayList<SubResultsModel> arrayList = new ArrayList<>();
        new Thread(() -> firebaseFirestore.collection(SUB).addSnapshotListener((value, error) -> {
            if (error != null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(error.getMessage(), null));
                return;
            }
            if (value == null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(FAILED, null));
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                SubResultsModel subResultsModel = dc.getDocument().toObject(SubResultsModel.class);
                switch (dc.getType()) {
                    case ADDED:
                        arrayList.add(subResultsModel);
                        break;
                    case MODIFIED:
                        int k = 0;
                        String id = subResultsModel.getId();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId().equals(id)) {
                                k = i;
                            }
                        }
                        arrayList.set(k, subResultsModel);
                        break;
                }
                callback.arrayFromRepository(ErrorHandlingRepositoryData.success(arrayList));
            }
        })).start();
    }
}
