package com.example.mtg.mainActivity.mainFragments.profile.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.logActivity.models.UserRegisterProfileModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<UserRegisterProfileModel> user;
    private static final String TAG = "MainActivity";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";


    public MutableLiveData<UserRegisterProfileModel> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
            loadData();
        }
        return user;
    }


    private void loadData() {
        new Thread(() -> FirebaseFirestore.getInstance()
                .collection(USERS)
                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.i(TAG, FAILED);
                        return;
                    }
                    if (value != null && value.exists()) {
                        UserRegisterProfileModel userProfile = value.toObject(UserRegisterProfileModel.class);
                        user.postValue(userProfile);
                    } else {
                        Log.i(TAG, FAILED);
                    }
                })).start();
    }


}
