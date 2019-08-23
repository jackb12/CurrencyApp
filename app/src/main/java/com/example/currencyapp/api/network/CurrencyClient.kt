package com.example.currencyapp.api.network

import com.example.currencyapp.api.response.InternalCurrency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyClient {

    @GET("latest")
    suspend fun getCurrencies(@Query("base") base: String): InternalCurrency

}