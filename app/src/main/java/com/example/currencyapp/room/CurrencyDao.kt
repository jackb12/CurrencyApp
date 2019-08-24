package com.example.currencyapp.room

import androidx.room.*

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyRate")
    fun getAll(): List<CurrencyRate>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currencyRates: List<CurrencyRate>)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrencies(vararg currencyRates: CurrencyRate)
}