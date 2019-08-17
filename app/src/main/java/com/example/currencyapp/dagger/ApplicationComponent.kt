package com.example.currencyapp.dagger

import dagger.Component

@Component(modules = [ApplicationModule::class, NetworkModule::class, ContextModule::class])
interface ApplicationComponent {


}