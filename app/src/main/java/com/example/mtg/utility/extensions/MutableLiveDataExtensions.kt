package com.example.mtg.utility.extensions

import androidx.lifecycle.MutableLiveData
import com.example.mtg.core.baseEvent.Event

fun <T> MutableLiveData<T>.newValue(newValue: T) {
    postValue(newValue)
}

fun <T> MutableLiveData<Event<T>>.newEvent(newEvent: T) {
    postValue(Event(newEvent))
}