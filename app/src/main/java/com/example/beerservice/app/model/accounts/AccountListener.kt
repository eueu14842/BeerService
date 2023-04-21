package com.example.beerservice.app.model.accounts

import com.example.beerservice.app.model.ResultState
import com.example.beerservice.app.model.Success

typealias Listener = (ResultState<UserTest>) -> Unit

class AccountListener(
    userCached: UserTest,
) {
    var resultCached: ResultState<UserTest>
    val listeners = mutableListOf<Listener>()

    init {
        resultCached = Success(userCached)
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
        notifyListeners()
    }

    fun notifyListeners() {
        listeners.forEach {
            it.invoke(resultCached)
        }
    }

}

data class UserTest(val userName: String)

fun main() {
    val userTest = UserTest("name")


}