package com.example.mtg.ui.activities.logActivity.logViewModel;

import androidx.lifecycle.ViewModel;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.userRepositories.LogRepository;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;
import com.example.mtg.repositories.repositoryCallbacks.UserFieldFromRepositoryCallback;

public class LogViewModel extends ViewModel {
    private final LogRepository logRepository = new LogRepository();

    public void sendResetPasswordEmail(String email,UpdateProfileCallback callback){
        logRepository.sendResetPasswordEmail(email, callback);
    }

    public void authUser(String email, String password, UpdateProfileCallback callback){
        logRepository.signInUser(email, password, callback);
    }

    public void createNewUser(UserRegisterProfileModel user, String password, UserFieldFromRepositoryCallback callback){
        logRepository.createNewUser(user, password, callback);
    }

}
