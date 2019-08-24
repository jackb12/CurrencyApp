package com.example.currencyapp

import java.text.DecimalFormat

object CurrencyConverter {

    private val currencyFormat = DecimalFormat("0.00")

    fun getFormattedAmount(rate: Float, amount: Float): String = currencyFormat.format(getAmount(amount, rate))


    fun getNumericalAmount(amount: String): Float = currencyFormat.parse(amount).toFloat()


    fun getAmount(amount: Float, rate: Float): Float = (amount * rate)
}