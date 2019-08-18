package com.example.currencyapp.dagger.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val REVOLUT_BASE_URL = "https://revolut.duckdns.org"
        private const val COUNTRIES_BASE_URL = "https://restcountries.eu"
        private const val TIMEOUT = 5
    }


    @Provides
    @Named("revolutConfig")
    @Singleton
    internal fun provideRevolutRetrofit(okHttpClientBuilder: OkHttpClient.Builder, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(REVOLUT_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClientBuilder.build())
            .build()


    @Provides
    @Named("countriesConfig")
    @Singleton
    internal fun provideCountriesRetrofit(okHttpClientBuilder: OkHttpClient.Builder, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClientBuilder.build())
            .build()


    @Provides
    @Singleton
    internal fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        (OkHttpClient.Builder()).connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)


    @Provides
    @Singleton
    internal fun provideGsonConverterFactory() = GsonConverterFactory.create()

}