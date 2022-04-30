package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.repositories.ErrorHandlerResourse.ErrorHandlingRepositoryData;

public interface UserFieldFromRepositoryCallback {
    void userFieldCallback(ErrorHandlingRepositoryData<String> userField);
}
