package com.example.beerservice.app.screens.main.feedback

import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beerservice.R
import com.example.beerservice.app.model.*
import com.example.beerservice.app.model.beers.BeersRepository
import com.example.beerservice.app.model.beers.entities.Beer
import com.example.beerservice.app.model.feedback.FeedbackRepository
import com.example.beerservice.app.screens.base.BaseViewModel
import com.example.beerservice.app.screens.main.auth.SignUpViewModel
import com.example.beerservice.app.utils.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FeedbackCreateViewModel(
    private val feedbackRepository: FeedbackRepository = Singletons.feedbackRepository,
    private val beerRepository: BeersRepository = Singletons.beerRepository
) : BaseViewModel() {


    private val _showToastEvent = MutableLiveEvent<Int>()
    val showToastEvent = _showToastEvent.share()

    private val _goBackEvent = MutableUnitLiveEvent()
    val goBackEvent = _goBackEvent.share()

    private val _state = MutableLiveData(State())
    val state = _state.share()

    private var beerId = MutableLiveData<Int>()
        get() = field


    private val _beer = MutableLiveData<ResultState<Beer>>()
    val beer = _beer.share()

    private val _imageName = MutableLiveEvent<String>()
    val imageName = _imageName.share()


    fun getBeerDetails() {
        viewModelScope.launch {
            println(beerId.value)
            val result = beerRepository.getBeerById(beerId.value!!)
            _beer.value = Pending()
            _beer.value = Success(result)
        }
    }

    fun setBeerId(value: Int) {
        if (this.beerId.value == value) return
        this.beerId.value = value
    }

    fun createFeedback(
        feedbackText: String,
        rating: Int,
        body:MultipartBody.Part
    ) {
        viewModelScope.launch {
            showProgress()
            try {
                val userId = accountsRepository.doGetProfile()
                feedbackRepository.createFeedback(
                    beerId.value!!,
                    feedbackText,
                    rating,
                    userId.userId!!,
                    body
                )
                showSuccessPublishedFeedbackMessage()
                goBack()
            } catch (e: EmptyFeedbackFieldException) {
                processEmptyFieldException(e)
            } finally {
                hideProgress()
            }
        }
    }



    private fun processEmptyFieldException(e: EmptyFeedbackFieldException) {
        _state.value = when (e.field) {
            FeedbackField.Text -> _state.requireValue()
                .copy(textErrorMessageRes = R.string.field_is_empty)
            FeedbackField.Rating -> _state.requireValue()
                .copy(ratingErrorMessageRes = R.string.field_is_empty)
        }
    }

    private fun showSuccessPublishedFeedbackMessage() =
        _showToastEvent.publishEvent(R.string.feedback_published)

    private fun goBack() = _goBackEvent.publishEvent()

    private fun showProgress() {
        _state.value = FeedbackCreateViewModel.State(signUpInProgress = true)
    }

    private fun hideProgress() {
        _state.value = _state.requireValue().copy(signUpInProgress = false)
    }

    data class State(
        @StringRes val textErrorMessageRes: Int = NO_ERROR_MESSAGE,
        @StringRes val ratingErrorMessageRes: Int = NO_ERROR_MESSAGE,
        val signUpInProgress: Boolean = false,
    ) {
        val showProgress: Boolean get() = signUpInProgress
        val enableViews: Boolean get() = !signUpInProgress
    }


    companion object {
        const val NO_ERROR_MESSAGE = 0
    }
}