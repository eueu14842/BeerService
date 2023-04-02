package com.example.beerservice.app.screens.main.auth

import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.screens.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel(
    val accountsRepository: AccountsRepository = Singletons.accountsRepository

) : BaseViewModel() {

    fun signIn(login: String, password: String) = viewModelScope.launch {
        accountsRepository.signIn(login, password)
    }
}