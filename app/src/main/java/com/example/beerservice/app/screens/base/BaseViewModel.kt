package com.example.beerservice.app.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerservice.R
import com.example.beerservice.app.model.AuthException
import com.example.beerservice.app.model.BackendException
import com.example.beerservice.app.model.ConnectionException
import com.example.beerservice.app.model.accounts.AccountsRepository
import com.example.beerservice.app.utils.Event
import com.example.beerservice.app.utils.logger.Logger
import com.example.beerservice.app.utils.publishEvent
import com.example.beerservice.app.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    val accountsRepository: AccountsRepository,

    ) : ViewModel() {
    @Inject
    lateinit var logger: Logger

    private val _showErrorMessageResEvent = MutableLiveData<Event<Int>>()
    val showErrorMessageResEvent = _showErrorMessageResEvent.share()

    private val _showErrorMessageEvent = MutableLiveData<Event<String>>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    private val _showAuthErrorAndRestartEvent = MutableLiveData<Event<Unit>>()
    val showAuthErrorAndRestartEvent = _showAuthErrorAndRestartEvent.share()


    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: ConnectionException) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.connection_error)
            } catch (e: BackendException) {
                logError(e)
                _showErrorMessageEvent.publishEvent(e.message ?: "")
            } catch (e: AuthException) {
                logError(e)
                _showAuthErrorAndRestartEvent.publishEvent()
            } catch (e: Exception) {
                logError(e)
                _showErrorMessageResEvent.publishEvent(R.string.internal_error)
            }
        }
    }


    fun logError(e: Throwable) {
        logger.error(javaClass.simpleName, e)
    }

    fun logout() {
        accountsRepository.logout()
    }


}