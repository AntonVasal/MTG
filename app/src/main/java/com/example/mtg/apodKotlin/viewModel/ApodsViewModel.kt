package com.example.mtg.apodKotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mtg.apodKotlin.model.ApodsModel
import com.example.mtg.apodKotlin.data.repositories.ApodRepository
import com.example.mtg.core.baseState.BaseStates
import com.example.mtg.core.baseViewModel.BaseStateViewModel
import com.example.mtg.utility.extensions.newEvent
import com.example.mtg.utility.extensions.newValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApodsViewModel @Inject constructor(private val apodRepository: ApodRepository) : BaseStateViewModel<BaseStates>() {

    private val _apods:MutableLiveData<ArrayList<ApodsModel>> = MutableLiveData()
    val apods:LiveData<ArrayList<ApodsModel>> = _apods

    fun getApods(){
        _state.newEvent(BaseStates.LoadingState)
        viewModelScope.launch {
            runCatching {
                apodRepository.getApods()
            }.onSuccess {
                _apods.newValue(it)
                _state.newEvent(BaseStates.SuccessState)
            }.onFailure {
                _state.newEvent(BaseStates.ErrorState(it.localizedMessage?:it.stackTraceToString()))
            }
        }
    }

}