package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

public interface UpdateProfileCallback {
    void updateProfileCallback(ErrorHandlingRepositoryData.Status status);
}
