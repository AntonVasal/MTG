package com.example.mtg.repositories.repositoryCallbacks;

import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public interface ArraysFromRepositoryCallback<E> {
    void arrayFromRepository(ErrorHandlingRepositoryData<ArrayList<E>> arrayFromRepository);
}
