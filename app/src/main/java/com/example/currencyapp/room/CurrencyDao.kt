package com.example.currencyapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyRate")
    fun getAll(): List<CurrencyRate>


    @Insert
    fun insertAll(currencyRates: List<CurrencyRate>)


    @Update
    fun updateCurrencies(vararg currencyRates: CurrencyRate)
}