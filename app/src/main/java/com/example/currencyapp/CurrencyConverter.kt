package com.example.currencyapp

import java.text.DecimalFormat

object CurrencyConverter {

    private val CURRENCY_FORMAT = DecimalFormat("0.00")

    fun getAmount(rate: Float, amount: Float): String = CURRENCY_FORMAT.format((amount * rate))
}