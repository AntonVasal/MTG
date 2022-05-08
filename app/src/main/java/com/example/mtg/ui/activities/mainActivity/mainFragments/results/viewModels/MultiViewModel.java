package com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.MultiResultsModel;
import com.example.mtg.repositories.MultiRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public class MultiViewModel extends ViewModel {
    private MutableLiveData<ErrorHandlingRepositoryData<ArrayList<MultiResultsModel>>> mutableLiveData;
    private final MultiRepository multiRepository = new MultiRepository();

    public MutableLiveData<ErrorHandlingRepositoryData<ArrayList<MultiResultsModel>>> getMutableLiveData(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    private void loadData() {
        multiRepository.loadMultiCollection(arrayFromRepository -> mutableLiveData.postValue(arrayFromRepository));
    }
}
