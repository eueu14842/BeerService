package com.example.beerservice.sources.accounts

import com.example.beerservice.app.model.accounts.AccountsSource
import com.example.beerservice.app.model.accounts.entities.SignUpData
import com.example.beerservice.app.model.accounts.entities.User
import com.example.beerservice.app.model.accounts.entities.UserEditData
import com.example.beerservice.app.model.place.entities.Place
import com.example.beerservice.sources.accounts.entites.SignInRequestEntity
import com.example.beerservice.sources.accounts.entites.SignUpRequestEntity
import com.example.beerservice.sources.accounts.entites.UserEditDataRequestEntity
import com.example.beerservice.sources.base.BaseRetrofitSource
import com.example.beerservice.sources.base.RetrofitConfig
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAccountSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    val accountApi = retrofit.create(AccountApi::class.java)

    override suspend fun signIn(
        login: String, password: String
    ): String = wrapRetrofitExceptions {
        val signInRequestEntity = SignInRequestEntity(login, password)
        accountApi.signIn(signInRequestEntity).token
    }

    override suspend fun signUp(signUpData: SignUpData) = wrapRetrofitExceptions {
        delay(1000)
        val signUpRequestEntity = SignUpRequestEntity(
            signUpData.tel,
            signUpData.mail,
            signUpData.userName,
            signUpData.login,
            signUpData.password
        )
        accountApi.signUp(signUpRequestEntity)
    }

    override suspend fun getUser(): User = wrapRetrofitExceptions {
        accountApi.getProfile().toUser()
    }

    override suspend fun getFavoritePlaces(userId: Int): List<Place> = wrapRetrofitExceptions {
        accountApi.getFavoritePlaces(userId).map { it.toPlace() }
    }


    override suspend fun updateAccount(userId: Int, userData: UserEditData) =
        wrapRetrofitExceptions {
            accountApi.editProfile(userId, userData.toUserEditDataEntity())
        }


}