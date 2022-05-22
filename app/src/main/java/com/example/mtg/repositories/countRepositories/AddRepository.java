package com.example.mtg.repositories.countRepositories;

import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.ArraysFromRepositoryCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;

public class AddRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String ADD = "add";
    private static final String FAILED = "Failed";
    private ListenerRegistration listenerRegistration;

    public AddRepository() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loadAddCollection(ArraysFromRepositoryCallback<AddResultsModel> callback) {
        ArrayList<AddResultsModel> arrayList = new ArrayList<>();
        new Thread(() -> listenerRegistration = firebaseFirestore.collection(ADD).addSnapshotListener((value, error) -> {
            if (error != null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(error.getMessage(), null));
                return;
            }
            if (value == null) {
                callback.arrayFromRepository(ErrorHandlingRepositoryData.error(FAILED, null));
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                AddResultsModel addResultsModel = dc.getDocument().toObject(AddResultsModel.class);
                switch (dc.getType()) {
                    case ADDED:
                        arrayList.add(addResultsModel);
                        break;
                    case MODIFIED:
                        int k = 0;
                        String id = addResultsModel.getId();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).getId().equals(id)) {
                                k = i;
                            }
                        }
                        arrayList.set(k, addResultsModel);
                        break;
                }
                callback.arrayFromRepository(ErrorHandlingRepositoryData.success(arrayList));
            }
        })).start();
    }

    public void removeListenerRegistration(){
        if (listenerRegistration!=null){
            listenerRegistration.remove();
        }
    }

}
