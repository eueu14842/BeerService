package com.example.beerservice.app.screens.main.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.R
import com.example.beerservice.app.model.EmptyFieldException
import com.example.beerservice.app.model.Field
import com.example.beerservice.app.model.InvalidCredentialsException
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.Event
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch
import java.time.Year

class SignInViewModel(

) : BaseViewModel() {

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private val _navigateToTabsEvent = MutableLiveData<Event<Unit>>()
    val navigateToTabsEvent = _navigateToTabsEvent.share()

    private val _showAuthErrorToastEvent = MutableLiveData<Int>()
    val showAuthToastEvent = _showAuthErrorToastEvent.share()

    fun signIn(login: String, password: String) = viewModelScope.launch {
        try {
            accountsRepository.signIn(login, password)
            launchTabsScreen()
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        } catch (e: InvalidCredentialsException) {
            processInvalidCredentialsException()
        }
    }

    private fun launchTabsScreen() = _navigateToTabsEvent.publishEvent(Unit)


    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.value?.copy(
            emptyLoginError = e.field == Field.Login,
            emptyPasswordError = e.field == Field.Password
        )
    }

    private fun processInvalidCredentialsException() {
        showAuthErrorToast()
    }

    private fun showAuthErrorToast() {
        _showAuthErrorToastEvent.value = R.string.invalid_login_or_password
    }

    data class State(
        val emptyLoginError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false,
    )
}