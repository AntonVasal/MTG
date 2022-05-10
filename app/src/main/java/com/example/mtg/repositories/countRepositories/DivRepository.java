package com.example.mtg.repositories.countRepositories;

import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.ArraysFromRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DivRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String DIV = "div";
    private static final String FAILED = "Failed";

    public DivRepository(){
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loadDivCollection(ArraysFromRepositoryCallback<DivResultsModel> divArray){
        ArrayList<DivResultsModel> arrayList = new ArrayList<>();
        new Thread(() -> firebaseFirestore.collection(DIV).addSnapshotListener((value, error) -> {
            if (error != null) {
                divArray.arrayFromRepository(ErrorHandlingRepositoryData.error(error.getMessage(), null));
                return;
            }
            if (value == null) {
                divArray.arrayFromRepository(ErrorHandlingRepositoryData.error(FAILED, null));
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                DivResultsModel divResultsModel = dc.getDocument().toObject(DivResultsModel.class);
                switch (dc.getType()) {
                    case ADDED:
                        arrayList.add(divResultsModel);
                        break;
                    case MODIFIED:
                        int k = 0;
                        String id = divResultsModel.getId();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId().equals(id)) {
                                k = i;
                            }
                        }
                        arrayList.set(k, divResultsModel);
                        break;
                }
                divArray.arrayFromRepository(ErrorHandlingRepositoryData.success(arrayList));
            }
        })).start();
    }
}
