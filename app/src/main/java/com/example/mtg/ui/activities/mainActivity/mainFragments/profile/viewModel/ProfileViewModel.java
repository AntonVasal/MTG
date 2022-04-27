package com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<UserRegisterProfileModel> user;
    private ProfileRepository profileRepository;
//    private UserRegisterProfileModel userRegisterProfileModel;
    private static final String TAG = "MainActivity";
    private static final String FAILED = "Failed";
    private static final String USERS = "users";


    public MutableLiveData<UserRegisterProfileModel> getUser() {
        profileRepository = new ProfileRepository();
        if (user == null) {
            user = new MutableLiveData<>();
            loadData();
        }
        return user;
    }

    private void loadData() {
//        new Thread(() -> FirebaseFirestore.getInstance()
//                .collection(USERS)
//                .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
//                .addSnapshotListener((value, error) -> {
//                    if (error != null) {
//                        Log.i(TAG, FAILED);
//                        return;
//                    }
//                    if (value != null && value.exists()) {
//                        userRegisterProfileModel = value.toObject(UserRegisterProfileModel.class);
//                        user.postValue(userRegisterProfileModel);
//                    } else {
//                        Log.i(TAG, FAILED);
//                    }
//                })).start();
        profileRepository.getUserData(userRegisterProfileModel -> user.postValue(userRegisterProfileModel));

    }

//    public void updateUserName(String name) {
//       user.postValue(profileRepository.updateUserName(name).getValue());
//    }


}
