package com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.ProfileRepository;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ErrorHandlingRepositoryData<UserRegisterProfileModel>> user;
    private ProfileRepository profileRepository;

    public MutableLiveData<ErrorHandlingRepositoryData<UserRegisterProfileModel>> getUser() {
        profileRepository = new ProfileRepository();
        if (user == null) {
            user = new MutableLiveData<>();
            loadData();
        }
        return user;
    }

    private void loadData() {
        profileRepository.getUserData(userRepoData -> user.postValue(userRepoData));
    }

    public void updateUserName(String name, UpdateProfileCallback updateProfileCallback) {
        profileRepository.updateUserName(name, updateProfileCallback);
    }

    public void updateUserCountry(String country, UpdateProfileCallback callback){
        profileRepository.updateUserCountry(country, callback);
    }


}
