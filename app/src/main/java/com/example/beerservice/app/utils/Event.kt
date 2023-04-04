package com.example.beerservice.app.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Event<T>(
    value: T
) {
    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }
}


fun <T> MutableLiveData<T>.share(): LiveData<T> = this


fun <T> MutableLiveData<Event<T>>.publishEvent(value: T) {
    this.value = Event(value)
}