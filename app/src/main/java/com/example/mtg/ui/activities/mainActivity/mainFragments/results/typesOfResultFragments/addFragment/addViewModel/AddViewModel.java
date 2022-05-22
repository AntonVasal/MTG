package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.addFragment.addViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.repositories.countRepositories.AddRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public class AddViewModel extends ViewModel {
    private final AddRepository addRepository = new AddRepository();
    private MutableLiveData<ErrorHandlingRepositoryData<ArrayList<AddResultsModel>>> addList;

    public MutableLiveData<ErrorHandlingRepositoryData<ArrayList<AddResultsModel>>> getUserResultsModel() {
        if (addList == null) {
            addList = new MutableLiveData<>();
            loadData();
        }
        return addList;
    }

    public void loadData(){
        addRepository.loadAddCollection(arrayFromRepository -> addList.postValue(arrayFromRepository));
    }

    public void removeCollectionListener(){
        addRepository.removeListenerRegistration();
    }
}
