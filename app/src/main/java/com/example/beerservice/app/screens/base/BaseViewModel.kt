package com.example.beerservice.app.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.utils.Event
import com.example.beerservice.app.utils.share

open class BaseViewModel(
    val accountsRepository: AccountsRepository = Singletons.accountsRepository
) : ViewModel() {


    private val _showErrorMessageResEvent = MutableLiveData<Event<Int>>()
    val showErrorMessageResEvent = _showErrorMessageResEvent.share()

    private val _showErrorMessageEvent = MutableLiveData<Event<String>>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    private val _showAuthErrorAndRestartEvent = MutableLiveData<Event<Unit>>()
    val showAuthErrorAndRestartEvent = _showAuthErrorAndRestartEvent.share()

    fun logout() {
        accountsRepository.logout()
    }
}