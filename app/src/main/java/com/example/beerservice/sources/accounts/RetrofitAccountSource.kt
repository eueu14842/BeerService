package com.example.beerservice.sources.accounts

import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.sources.accounts.entites.SignInRequestEntity
import com.example.beerservice.sources.accounts.entites.SignUpRequestEntity
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.delay

class RetrofitAccountSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    val accountApi = retrofit.create(AccountApi::class.java)

    override suspend fun signIn(
        login: String,
        password: String
    ): String = wrapRetrofitExceptions {
        val signInRequestEntity = SignInRequestEntity(login, password)
        accountApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(signUpData: SignUpData) = wrapRetrofitExceptions {
        accountApi.signUp(signUpData.toSignUpRequestEntity())
    }

    override suspend fun getUser(): User = wrapRetrofitExceptions {
        accountApi.getProfile().toUser()
    }


}