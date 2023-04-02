package com.example.beerservice.sources.accounts

import com.example.beerservice.sources.accounts.entites.SignInRequestEntity
import com.example.beerservice.sources.accounts.entites.SignUpRequestEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountApi {

    @POST("user/create")
    fun signUp(@Body body: SignUpRequestEntity)

    @POST("/user/auth")
    fun signIn(@Body signInRequestEntity: SignInRequestEntity)
}