package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.models.profileModel.UserRegisterProfileModel;
import com.example.mtg.repositories.ErrorHandlingRepositoryData;

public interface UserRepositoryCallback {
    void userRepoCallback(ErrorHandlingRepositoryData<UserRegisterProfileModel> userRepoData);
}
