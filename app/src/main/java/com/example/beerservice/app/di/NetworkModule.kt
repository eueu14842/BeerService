package com.example.beerservice.app.di

import com.example.beerservice.app.Const
import com.example.beerservice.app.model.Singletons
import com.example.beerservice.app.model.SourcesProvider
import com.example.beerservice.app.model.settings.AppSettings
import com.example.beerservice.sources.base.RetrofitConfig
import com.example.beerservice.sources.base.RetrofitSourcesProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(createLoggingInterceptor())
            .addInterceptor(createAuthorizationInterceptor(Singletons.appSettings)).build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createAuthorizationInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", "Bearer $token")
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }
}