package com.example.currencyapp.dagger.modules

import com.example.currencyapp.api.network.CountriesClient
import com.example.currencyapp.api.network.CurrencyClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideRevolutApiClient(@Named("revolutConfig") retrofit: Retrofit) = retrofit.create(CurrencyClient::class.java)

    @Provides
    @Singleton
    internal fun provideCountriesApiClient(@Named("countriesConfig") retrofit: Retrofit) = retrofit.create(CountriesClient::class.java)
}