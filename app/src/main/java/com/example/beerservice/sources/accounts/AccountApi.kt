package com.example.beerservice.sources.accounts

import com.example.beerservice.sources.accounts.entites.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountApi {

    @POST("user/create")
    suspend fun signUp(@Body body: SignUpRequestEntity)

    @POST("user/auth")
    suspend fun signIn(@Body signInRequestEntity: SignInRequestEntity): SignInResponseEntity

    @GET("user/profile")
    suspend fun getProfile(): GetUserResponseEntity

    @POST("user/edit")
    suspend fun editProfile(
        @Query("id") userId: Int,
        @Body body: UserEditDataRequestEntity
    )
}