package com.example.currencyapp.room

data class CurrencyRates(
    private val currencyCode: String,
    private val currencyDescription: String,
    private val currencyFlag: String? = null,
    private val currencyRate: Float
)