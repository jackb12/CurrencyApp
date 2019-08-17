package com.example.currencyapp.dagger

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://revolut.duckdns.org"
        private const val TIMEOUT = 1
    }

    @Provides
    @Singleton
    internal fun provideRetorfit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()


    @Provides
    @Singleton
    internal fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        (OkHttpClient.Builder()).connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)

}