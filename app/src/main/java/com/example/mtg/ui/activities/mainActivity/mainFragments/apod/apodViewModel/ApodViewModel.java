package com.example.mtg.ui.activities.mainActivity.mainFragments.apod.apodViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtg.models.apodModel.ApodModel;
import com.example.mtg.repositories.apodRepository.ApodRepository;
import com.example.mtg.repositories.errorHandlerResourse.ErrorHandlingRepositoryData;

import java.util.ArrayList;

public class ApodViewModel extends ViewModel {

    private MutableLiveData<ErrorHandlingRepositoryData<ArrayList<ApodModel>>> apodList;
    private final ApodRepository apodRepository = new ApodRepository();

    public MutableLiveData<ErrorHandlingRepositoryData<ArrayList<ApodModel>>> getApodList() {
        if (apodList == null) {
            apodList = new MutableLiveData<>();
            loadData();
        }
        return apodList;
    }

    private void loadData() {
        apodRepository.getApodLists(data -> apodList.postValue(data));
    }
}
