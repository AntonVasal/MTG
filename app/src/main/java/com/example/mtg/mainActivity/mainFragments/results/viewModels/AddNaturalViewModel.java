package com.example.mtg.mainActivity.mainFragments.results.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.example.mtg.mainActivity.count.countModels.AddResultsModel;
import com.example.mtg.mainActivity.mainFragments.results.models.UserResultsModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddNaturalViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserResultsModel>> userResultsModel;
    ArrayList<UserResultsModel> arrayList = new ArrayList<>();
    String id;
    String imageUrl;
    String name;
    int score;
    int k;
    FirebaseFirestore firebaseFirestore;

    public MutableLiveData<ArrayList<UserResultsModel>> getUserResultsModel() {
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
                    switch (dc.getType()){
                        case ADDED:
                            id = addResultsModel.getId();
                            firebaseFirestore.collection("users").document(id)
                                    .get().addOnSuccessListener(documentSnapshot -> {
                                UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                                score = addResultsModel.getAddNaturalScore();
                                assert userRegisterProfileModel != null;
                                imageUrl = userRegisterProfileModel.getImageUrl();
                                name = userRegisterProfileModel.getName();
                                arrayList.add(new UserResultsModel(name, imageUrl, score));
                            });
                            break;
                        case MODIFIED:
                            id = addResultsModel.getId();
                            firebaseFirestore.collection("users").document(id)
                                    .get().addOnSuccessListener(documentSnapshot -> {
                                UserRegisterProfileModel userRegisterProfileModel = documentSnapshot.toObject(UserRegisterProfileModel.class);
                                score = addResultsModel.getAddNaturalScore();
                                assert userRegisterProfileModel != null;
                                imageUrl = userRegisterProfileModel.getImageUrl();
                                name = userRegisterProfileModel.getName();
                                for (int i = 1; i < arrayList.size() ; i++) {
                                    if(arrayList.get(i).getName().equals(name)  && arrayList.get(i).getImage().equals(imageUrl)){
                                        k = i;
                                    }
                                }
                                arrayList.set(k,new UserResultsModel(name, imageUrl, score));
                            });
                            break;
                    }
                }
            });
            userResultsModel.postValue(arrayList);
        }).start();
    }
}
