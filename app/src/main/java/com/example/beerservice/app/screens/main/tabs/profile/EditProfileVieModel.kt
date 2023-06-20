package com.example.beerservice.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.R
import com.example.beerservice.app.model.EmptyFieldException
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Success
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.accounts.entities.UserEditData
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.MutableLiveEvent
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.launch

class EditProfileVieModel : BaseViewModel() {

    private val _initialEditEvent = MutableLiveEvent<UserEditData>()
    val initialEditEvent = _initialEditEvent.share()

    private val _userIdState = MutableLiveData<Int?>()
    val userIdState = _userIdState.share()

    private val _saveInProgress = MutableLiveData(false)
    val saveInProgress = _saveInProgress.share()

    private val _showErrorEvent = MutableLiveEvent<Int>()
    val showErrorEvent = _showErrorEvent.share()

    init {
        viewModelScope.launch {
            val result: ResultState<User> = accountsRepository.getAccount()
            result.map {
                _userIdState.value = it.userId
                if (result is Success) _initialEditEvent.publishEvent(it.toUserEditData())
            }
        }
    }

   suspend fun publishAccount() {
        accountsRepository.refreshUser()
    }

    fun updateAccount(userId: Int, userEditData: UserEditData) = viewModelScope.safeLaunch {
        showProgress()
        try {
            accountsRepository.updateAccount(userId, userEditData)
        } catch (e: EmptyFieldException) {
            showEmptyFieldErrorMessage()
        } finally {
            hideProgress()
        }
    }

    private fun showProgress() {
        _saveInProgress.value = true
    }

    private fun hideProgress() {
        _saveInProgress.value = false
    }

    private fun showEmptyFieldErrorMessage() = _showErrorEvent.publishEvent(R.string.field_is_empty)
}