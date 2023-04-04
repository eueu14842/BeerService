package com.example.beerservice.app.screens.main.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.EmptyFieldException
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.Event
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class SignInViewModel(
    val accountsRepository: AccountsRepository = Singletons.accountsRepository

) : BaseViewModel() {


    private val _navigateToTabsEvent = MutableLiveData<Event<Unit>>()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    fun signIn(login: String, password: String) = viewModelScope.launch {
        // TODO: показывать прогресс, скрыть прогресс, InvalidCredentials?
        try {
            accountsRepository.signIn(login, password)
            launchTabsScreen()
        } catch (e: EmptyFieldException) {
            throw  e
        }
    }

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent(Unit)

    data class State(
        val emptyLoginError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false,
    )
}