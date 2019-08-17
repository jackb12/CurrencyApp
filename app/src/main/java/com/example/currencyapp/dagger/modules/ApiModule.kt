package com.example.currencyapp.dagger.modules

import com.example.currencyapp.api.network.CurrencyClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideApiClient(retrofit: Retrofit) = retrofit.create(CurrencyClient::class.java)
}