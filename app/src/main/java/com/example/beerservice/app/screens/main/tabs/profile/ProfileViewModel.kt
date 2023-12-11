package com.example.beerservice.app.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.feedback.FeedbackRepository
import com.example.beerservice.app.model.feedback.entities.FeedbackBeer
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.utils.share
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val feedbackRepository: FeedbackRepository,
    accountsRepository: AccountsRepository
) :
    BaseViewModel(accountsRepository) {

    private val _profile = MutableLiveData<ResultState<User>>()
    val profile = _profile.share()

    var feedback: Flow<PagingData<FeedbackBeer>>? = null
    private var userId = MutableLiveData(0)


    suspend fun getBeerByUserId() {
        userId.value = accountsRepository.doGetProfile().userId
        viewModelScope.launch {
            feedback = userId.asFlow()
                .debounce(400)
                .flatMapLatest {
                    feedbackRepository.getPagedFeedbackByUserId(it)
                }
                .cachedIn(viewModelScope)
        }
    }

}