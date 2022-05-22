package com.example.mtg.ui.activities.mainActivity.mainFragments.results.resultsViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultsViewModel extends ViewModel {

    MutableLiveData<Boolean> isOnPause = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsOnPause() {
        return isOnPause;
    }

    public void setIsOnPause(Boolean status) {
        isOnPause.postValue(status);
    }
}
