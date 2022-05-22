package com.example.mtg.core.baseViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mtg.core.baseEvent.Event
import com.example.mtg.core.baseState.BaseState


open class BaseStateViewModel<State : BaseState> : ViewModel() {
    val _state = MutableLiveData<Event<State>>()
    val state : LiveData<Event<State>> = _state
}