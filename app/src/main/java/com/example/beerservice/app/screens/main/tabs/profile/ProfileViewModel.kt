package com.example.beerservice.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.app.model.Pending
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class ProfileViewModel() :
    BaseViewModel() {

    private val _profile = MutableLiveData<ResultState<User>>()
    val profile = _profile.share()

    fun getProfile() {
        viewModelScope.launch {
            val profile = accountsRepository.doGetProfile()
            _profile.value = Pending()
            _profile.value = Success(profile)
        }
    }


}