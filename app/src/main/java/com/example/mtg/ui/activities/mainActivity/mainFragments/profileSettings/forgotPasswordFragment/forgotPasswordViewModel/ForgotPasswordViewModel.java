package com.example.mtg.ui.activities.mainActivity.mainFragments.profileSettings.forgotPasswordFragment.forgotPasswordViewModel;

import androidx.lifecycle.ViewModel;

import com.example.mtg.repositories.ProfileRepository;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;

public class ForgotPasswordViewModel extends ViewModel {

    private final ProfileRepository profileRepository = new ProfileRepository();

    public void sendResetPasswordEmail(UpdateProfileCallback callback){
        profileRepository.sendPasswordResetEmail(callback);
    }

}
