package com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.userRepositories.ProfileRepository;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ErrorHandlingRepositoryData<UserRegisterProfileModel>> user;
    private final ProfileRepository profileRepository = new ProfileRepository();

    public MutableLiveData<ErrorHandlingRepositoryData<UserRegisterProfileModel>> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
            loadData();
        }
        return user;
    }

    public void loadData() {
        profileRepository.getUserData(userRepoData -> user.postValue(userRepoData));
    }

    public void updateUserImage(Uri uri, String imageName, UserFieldFromRepositoryCallback callback){
        profileRepository.updateUserProfileImage(uri, imageName, callback);
    }

    public void removeListenerRegistration(){
        profileRepository.removeListener();
    }

}
