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
    FirebaseFirestore firebaseFirestore;

    public MutableLiveData<ArrayList<UserResultsModel>> getUserResultsModel(){
        if (userResultsModel == null){
            userResultsModel = new MutableLiveData<>();
            loadData();
        }
        return userResultsModel;
    }

    private void loadData() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("add").addSnapshotListener((value, error) -> {
            if (error != null){
                Log.i("MainActivity","Failed");
                return;
            }
            assert value != null;
            for (DocumentChange dc: value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED){
                    AddResultsModel addResultsModel = dc.getDocument().toObject(AddResultsModel.class);
                    id = addResultsModel.getId();
                    FirebaseFirestore.getInstance().collection("users").document(id)
                            .addSnapshotListener((value1, error1) -> {
                                if (error1 != null){
                                    Log.i("MainActivity","Failed");
                                    return;
                                }
                                if (value1 != null && value1.exists()){
                                    UserRegisterProfileModel userRegisterProfileModel = value1.toObject(UserRegisterProfileModel.class);
                                    score = addResultsModel.getAddNaturalScore();
                                    assert userRegisterProfileModel != null;
                                    imageUrl = userRegisterProfileModel.getImageUrl();
                                    name = userRegisterProfileModel.getName();
                                    arrayList.add(new UserResultsModel(name,imageUrl,score));
                                }
                            });
                }
            }
            userResultsModel.postValue(arrayList);
        });
    }
}
