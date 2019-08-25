package com.example.currencyapp

import android.app.Application
import com.example.currencyapp.dagger.ApplicationComponent
import com.example.currencyapp.dagger.modules.ApplicationModule
import com.example.currencyapp.dagger.DaggerApplicationComponent

class BaseApplication : Application() {

    companion object {
        private var instance: BaseApplication? = null
        private var applicationComponent: ApplicationComponent? = null

        fun getInstance(): BaseApplication? {
            return instance
        }

        fun getComponent(): ApplicationComponent? {
            return applicationComponent
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(
                this
            )
        ).build()
    }


    fun setConnectionListener(listener: NetworkReceiver.ConnectionListener) {
        NetworkReceiver.connectionListener = listener
    }
}