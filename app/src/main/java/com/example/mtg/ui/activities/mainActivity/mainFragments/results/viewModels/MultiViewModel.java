package com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.MultiResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MultiResultsModel>> mutableLiveData;
    ArrayList<MultiResultsModel> arrayList = new ArrayList<>();
    private int k;
    private String id;
    private FirebaseFirestore firebaseFirestore;
    private static final String TAG = "MainActivity";
    private static final String MULTI = "multi";
    private static final String FAILED = "Failed";

    public MutableLiveData<ArrayList<MultiResultsModel>> getMutableLiveData(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    private void loadData() {
        new Thread(() -> {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection(MULTI).addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.i(TAG, FAILED);
                    return;
                }
                assert value != null;
                for (DocumentChange dc: value.getDocumentChanges()) {
                    MultiResultsModel multiResultsModel = dc.getDocument().toObject(MultiResultsModel.class);
                    switch (dc.getType()){
                        case ADDED:
                            arrayList.add(multiResultsModel);
                            break;
                        case MODIFIED:
                            id = multiResultsModel.getId();
                            for (int i = 0; i < arrayList.size() ; i++) {
                                if (arrayList.get(i).getId().equals(id)){
                                    k = i;
                                }
                            }
                            arrayList.set(k,multiResultsModel);
                            break;
                    }
                }
                mutableLiveData.postValue(arrayList);
            });
        }).start();
    }
}
