package com.example.currencyapp.dagger.modules

import android.content.Context
import androidx.room.Room
import com.example.currencyapp.room.AppDatabase
import com.example.currencyapp.room.CurrencyDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    internal fun provideRoomDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()


    @Provides
    @Singleton
    internal fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao = appDatabase.currencyDao()
}