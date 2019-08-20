package com.example.currencyapp.dagger.modules

import android.content.Context
import androidx.room.Room
import com.example.currencyapp.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    internal fun provideRoomDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )


    @Provides
    @Singleton
    internal fun provideCurrencyDao(appDatabase: AppDatabase) = appDatabase.currencyDao()
}