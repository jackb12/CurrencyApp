package com.example.currencyapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency")
    fun getAll(): List<CurrencyRates>


    @Insert
    fun insertAll(vararg currencyRates: CurrencyRates)


    @Update
    fun updateCurrencies(vararg currencyRates: CurrencyRates)
}