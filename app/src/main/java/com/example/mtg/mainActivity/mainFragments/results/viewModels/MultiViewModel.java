package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.mainActivity.count.countModels.MultiResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MultiViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MultiResultsModel>> mutableLiveData;
    ArrayList<MultiResultsModel> arrayList = new ArrayList<>();
    int k;
    String id;
    FirebaseFirestore firebaseFirestore;

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
            firebaseFirestore.collection("multi").addSnapshotListener((value, error) -> {
                if (error != null) {
                    Log.i("MainActivity", "Failed");
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
                            for (int i = 1; i < arrayList.size() ; i++) {
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
