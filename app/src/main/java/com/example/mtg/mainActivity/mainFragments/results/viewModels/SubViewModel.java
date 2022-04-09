package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.mainActivity.count.countModels.SubResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SubViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SubResultsModel>> mutableLiveData;
    ArrayList<SubResultsModel> arrayList = new ArrayList<>();
    private int k;
    private String id;
    private FirebaseFirestore firebaseFirestore;

    public MutableLiveData<ArrayList<SubResultsModel>> getMutableLiveData(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    private void loadData() {
        new Thread(() -> {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("sub").addSnapshotListener(
                    (value, error) -> {
                        if (error != null) {
                            Log.i("MainActivity", "Failed");
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc: value.getDocumentChanges()) {
                            SubResultsModel subResultsModel = dc.getDocument().toObject(SubResultsModel.class);
                            switch (dc.getType()){
                                case ADDED:
                                    arrayList.add(subResultsModel);
                                    break;
                                case MODIFIED:
                                    id = subResultsModel.getId();
                                    for (int i = 0; i < arrayList.size() ; i++) {
                                        if (arrayList.get(i).getId().equals(id)){
                                            k = i;
                                        }
                                    }
                                    arrayList.set(k,subResultsModel);
                            }
                        }
                        mutableLiveData.postValue(arrayList);
                    }
            );
        }).start();
    }
}
