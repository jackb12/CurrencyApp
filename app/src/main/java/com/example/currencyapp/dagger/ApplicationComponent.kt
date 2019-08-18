package com.example.currencyapp.dagger

import com.example.currencyapp.api.request.GetCountries
import com.example.currencyapp.api.request.GetCurrencies
import com.example.currencyapp.dagger.modules.ApiModule
import com.example.currencyapp.dagger.modules.ApplicationModule
import com.example.currencyapp.dagger.modules.ContextModule
import com.example.currencyapp.dagger.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ContextModule::class, ApiModule::class])
interface ApplicationComponent {

    fun inject(getCurrencies: GetCurrencies)
    fun inject(getCountries: GetCountries)
}