package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.models.apodModel.ApodModel;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public interface ApodListCallback {
    void apodListFromRepository(ErrorHandlingRepositoryData<ArrayList<ApodModel>> apodList);
}
