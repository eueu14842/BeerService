package com.example.beerservice.sources

import com.example.beerservice.app.Const.BASE_URL
import com.example.beerservice.app.SourcesProvider
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.settings.AppSettings
import com.example.beerservice.sources.base.RetrofitConfig
import com.example.beerservice.sources.base.RetrofitSourcesProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SourceProviderHolder {
    val sourcesProvider: SourcesProvider by lazy {
        val config = RetrofitConfig(createRetrofit())
        RetrofitSourcesProvider(config)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthorizationInterceptor(Singletons.appSettings))
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createAuthorizationInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", token)
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }
}