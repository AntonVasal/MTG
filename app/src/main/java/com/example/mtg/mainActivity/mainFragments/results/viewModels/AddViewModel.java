package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.mainActivity.countFragment.countModels.AddResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddViewModel extends ViewModel {
    private static final String TAG = "MainActivity";
    private static final String ADD = "add";
    private static final String FAILED = "Failed";
    private MutableLiveData<ArrayList<AddResultsModel>> userResultsModel;
    ArrayList<AddResultsModel> arrayList = new ArrayList<>();
    private int k;
    private String id;

    private FirebaseFirestore firebaseFirestore;

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
            firebaseFirestore.collection(ADD).addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.i(TAG, FAILED);
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
