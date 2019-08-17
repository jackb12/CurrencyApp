package com.example.currencyapp.dagger.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ContextModule {

    @Provides
    @Singleton
    fun proivdeContext(application: Application): Context? {
        return application.applicationContext
    }
}