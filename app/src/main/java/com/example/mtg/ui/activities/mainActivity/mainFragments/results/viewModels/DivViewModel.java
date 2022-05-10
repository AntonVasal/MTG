package com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.DivResultsModel;
import com.example.mtg.repositories.countRepositories.DivRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public class DivViewModel extends ViewModel {
    private MutableLiveData<ErrorHandlingRepositoryData<ArrayList<DivResultsModel>>> mutableLiveData;
    private final DivRepository divRepository = new DivRepository();

    public MutableLiveData<ErrorHandlingRepositoryData<ArrayList<DivResultsModel>>> getMutableLiveData(){
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            loadData();
        }
        return mutableLiveData;
    }

    private void loadData() {
       divRepository.loadDivCollection(arrayFromRepository -> mutableLiveData.postValue(arrayFromRepository));
    }
}
