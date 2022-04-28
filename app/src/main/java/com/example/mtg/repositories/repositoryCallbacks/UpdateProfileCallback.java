package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;

public interface UpdateProfileCallback {
    void updateProfileCallback(ErrorHandlingRepositoryData.Status status);
}
