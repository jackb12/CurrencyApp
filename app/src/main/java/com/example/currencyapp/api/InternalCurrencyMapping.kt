package com.example.currencyapp.api

import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.response.InternalCurrency

object InternalCurrencyMapping {

    fun mapCurrency(response: InternalCurrency?) : Currency? = response?.let {
        Currency(
            response.base,
            response.rates
        )
    }
}