package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

public interface UserFieldFromRepositoryCallback {
    void userFieldCallback(ErrorHandlingRepositoryData<String> userField);
}
