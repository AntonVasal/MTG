package com.example.mtg.ui.activities.logActivity.logViewModel;

import androidx.lifecycle.ViewModel;

import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;
import com.example.mtg.repositories.LogRepository;
import com.example.mtg.repositories.repositoryCallbacks.UpdateProfileCallback;

public class LogViewModel extends ViewModel {
    private final LogRepository logRepository = new LogRepository();

    public void sendResetPasswordEmail(String email,UpdateProfileCallback callback){
        logRepository.sendResetPasswordEmail(email, callback);
    }

    public void authUser(String email, String password, UpdateProfileCallback callback){
        logRepository.signInUser(email, password, callback);
    }


}
