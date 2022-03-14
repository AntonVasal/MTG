package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddViewModel extends ViewModel {
    private MutableLiveData<ArrayList<AddResultsModel>> userResultsModel;
    ArrayList<AddResultsModel> arrayList = new ArrayList<>();
    int k;
    String id;

    FirebaseFirestore firebaseFirestore;

    public MutableLiveData<ArrayList<AddResultsModel>> getUserResultsModel() {
        if (userResultsModel == null) {
            userResultsModel = new MutableLiveData<>();
            loadData();
        }
        return userResultsModel;
    }

    private void loadData() {
        new Thread(() -> {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("add").addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.i("MainActivity", "Failed");
                    return;
                }
                assert value != null;
                for (DocumentChange dc : value.getDocumentChanges()) {
                    AddResultsModel addResultsModel = dc.getDocument().toObject(AddResultsModel.class);
                    switch (dc.getType()) {
                        case ADDED:
                            arrayList.add(addResultsModel);
                            break;
                        case MODIFIED:
                            id = addResultsModel.getId();
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList.get(i).getId().equals(id)){
                                    k=i;
                                }
                            }
                            arrayList.set(k,addResultsModel);
                            break;
                    }
                }
                userResultsModel.postValue(arrayList);
            });
        }).start();
    }
}
