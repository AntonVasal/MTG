package com.example.mtg.ui.activities.mainActivity.mainFragments.profile.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.ProfileRepository;

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

    public void removeListenerRegistration(){
        profileRepository.removeListener();
    }

}
