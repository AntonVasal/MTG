package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.mainActivity.count.countModels.DivResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DivViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DivResultsModel>> mutableLiveData;
    ArrayList<DivResultsModel> arrayList = new ArrayList<>();
    int k;
    String id;
    FirebaseFirestore firebaseFirestore;

    public MutableLiveData<ArrayList<DivResultsModel>> getMutableLiveData(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    private void loadData() {
        new Thread(() -> {
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("div").addSnapshotListener(
                    (value, error) -> {
                        if (error != null) {
                            Log.i("MainActivity", "Failed");
                            return;
                        }
                        assert value != null;
                        for (DocumentChange dc: value.getDocumentChanges()) {
                            DivResultsModel divResultsModel = dc.getDocument().toObject(DivResultsModel.class);
                            switch (dc.getType()){
                                case ADDED:
                                    arrayList.add(divResultsModel);
                                    break;
                                case MODIFIED:
                                    id = divResultsModel.getId();
                                    for (int i = 0; i < arrayList.size() ; i++) {
                                        if (arrayList.get(i).getId().equals(id)){
                                            k = i;
                                        }
                                    }
                                    arrayList.set(k,divResultsModel);
                            }
                        }
                        mutableLiveData.postValue(arrayList);
                    }
            );
        }).start();
    }
}
