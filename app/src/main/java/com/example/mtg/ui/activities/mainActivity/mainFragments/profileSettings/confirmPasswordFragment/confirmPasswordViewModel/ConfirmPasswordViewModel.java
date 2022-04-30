package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.confirmPasswordFragment.confirmPasswordViewModel;

import androidx.lifecycle.ViewModel;

import com.example.mtg.repositories.ProfileRepository;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;

public class ConfirmPasswordViewModel extends ViewModel {

    private final ProfileRepository profileRepository = new ProfileRepository();

    public void reAuthCurrentUser(String password, UpdateProfileCallback updateProfileCallback){
        profileRepository.reAuthCurrentUser(password, updateProfileCallback);
    }
}
