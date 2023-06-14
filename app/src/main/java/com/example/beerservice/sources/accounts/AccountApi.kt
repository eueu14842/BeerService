package com.example.beerservice.sources.accounts

import com.example.beerservice.sources.accounts.entites.GetUserResponseEntity
import com.example.beerservice.sources.accounts.entites.SignInRequestEntity
import com.example.beerservice.sources.accounts.entites.SignInResponseEntity
import com.example.beerservice.sources.accounts.entites.SignUpRequestEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApi {

    @POST("user/create")
    suspend fun signUp(@Body body: SignUpRequestEntity)

    @POST("user/auth")
    suspend fun signIn(@Body signInRequestEntity: SignInRequestEntity): SignInResponseEntity

    @GET("user/profile")
    suspend fun getProfile(): GetUserResponseEntity
}