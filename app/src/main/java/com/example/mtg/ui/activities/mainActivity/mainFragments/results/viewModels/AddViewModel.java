package com.example.mtg.ui.activities.mainActivity.mainFragments.results.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.countModels.AddResultsModel;
import com.example.mtg.repositories.AddRepository;
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

    private void loadData(){
        addRepository.loadAddCollection(arrayFromRepository -> addList.postValue(arrayFromRepository));
    }
}
