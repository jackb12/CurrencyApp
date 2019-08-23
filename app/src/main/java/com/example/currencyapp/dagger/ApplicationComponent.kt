package com.example.currencyapp.dagger

import com.example.currencyapp.CurrencyViewModel
import com.example.currencyapp.dagger.modules.*
import com.example.currencyapp.repository.CurrencyRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ApiModule::class, RoomModule::class])
interface ApplicationComponent {

    fun inject(currencyRepository: CurrencyRepository)
    fun inject(currencyViewModel: CurrencyViewModel)
}