package com.example.currencyapp.dagger.modules

import android.content.Context
import com.example.currencyapp.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(
    private val baseApplication: BaseApplication
) {

    @Provides
    @Singleton
    internal fun provideApplication() = baseApplication


    @Provides fun provideContext(): Context = baseApplication.applicationContext


}