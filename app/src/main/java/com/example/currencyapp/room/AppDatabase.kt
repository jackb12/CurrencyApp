package com.example.currencyapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyapp.room.AppDatabase.Companion.VERSION

@Database(entities = [CurrencyRates::class], version = VERSION)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "currencyrates.db"
    }

    abstract fun currencyDao(): CurrencyDao
}