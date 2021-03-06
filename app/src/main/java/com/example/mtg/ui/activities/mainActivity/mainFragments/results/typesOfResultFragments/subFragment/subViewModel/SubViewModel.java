package com.example.mtg.ui.activities.mainActivity.mainFragments.results.typesOfResultFragments.subFragment.subViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.SubResultsModel;
import com.example.mtg.repositories.countRepositories.SubRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public class SubViewModel extends ViewModel {
    private MutableLiveData<ErrorHandlingRepositoryData<ArrayList<SubResultsModel>>> mutableLiveData;
    private final SubRepository subRepository = new SubRepository();

    public MutableLiveData<ErrorHandlingRepositoryData<ArrayList<SubResultsModel>>> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    public void loadData() {
        subRepository.loadSubCollection(arrayFromRepository -> mutableLiveData.postValue(arrayFromRepository));
    }

    public void removeCollectionListener() {
        subRepository.removeListenerRegistration();
    }
}
