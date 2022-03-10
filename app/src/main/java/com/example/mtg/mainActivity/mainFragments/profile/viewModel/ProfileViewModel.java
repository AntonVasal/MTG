package com.example.mtg.mainActivity.mainFragments.profile.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<UserRegisterProfileModel> user;

    public MutableLiveData<UserRegisterProfileModel> getUser(){
        if (user == null){
            user = new MutableLiveData<>();
            loadData();
        }
        return user;
    }


    private void loadData(){
         FirebaseFirestore.getInstance()
                .collection("users")
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .get().addOnSuccessListener(documentSnapshot -> {
            UserRegisterProfileModel userProfile = documentSnapshot.toObject(UserRegisterProfileModel.class);
            user.postValue(userProfile);
        });

    }
}
