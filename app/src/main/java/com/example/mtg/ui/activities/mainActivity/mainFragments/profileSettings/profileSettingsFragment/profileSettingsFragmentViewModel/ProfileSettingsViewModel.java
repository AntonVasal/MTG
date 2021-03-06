package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.profileSettingsFragment.profileSettingsFragmentViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.userRepositories.ProfileRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;

public class ProfileSettingsViewModel extends ViewModel {
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

    public void updateUserName(String name, UpdateProfileCallback updateProfileCallback) {
        profileRepository.updateUserName(name, updateProfileCallback);
    }

    public void updateUserCountry(String country, UpdateProfileCallback callback){
        profileRepository.updateUserCountry(country, callback);
    }

    public void updateUserSurname(String surname, UpdateProfileCallback updateProfileCallback){
        profileRepository.updateUserSurname(surname, updateProfileCallback);
    }

    public void updatePassword(String password, UpdateProfileCallback callback){
        profileRepository.updatePassword(password, callback);
    }

    public void updateEmail(String email, UserFieldFromRepositoryCallback callback){
        profileRepository.updateUserEmail(email, callback);
    }

    public void updateNickname(String nickname, UserFieldFromRepositoryCallback callback){
        profileRepository.updateUserNickname(nickname, callback);
    }

    public void removeListener(){
        profileRepository.removeListener();
    }

}
