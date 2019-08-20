package com.example.currencyapp.dagger

import com.example.currencyapp.CurrencyViewModel
import com.example.currencyapp.api.request.GetCountries
import com.example.currencyapp.api.request.GetCurrencies
import com.example.currencyapp.dagger.modules.ApiModule
import com.example.currencyapp.dagger.modules.ApplicationModule
import com.example.currencyapp.dagger.modules.ContextModule
import com.example.currencyapp.dagger.modules.NetworkModule
import com.example.currencyapp.repository.CurrencyRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ContextModule::class, ApiModule::class])
interface ApplicationComponent {

    fun inject(getCurrencies: GetCurrencies)
    fun inject(getCountries: GetCountries)
    fun inject(currencyRepository: CurrencyRepository)
    fun inject(currencyViewModel: CurrencyViewModel)
}