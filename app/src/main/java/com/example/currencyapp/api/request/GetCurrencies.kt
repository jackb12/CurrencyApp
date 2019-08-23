package com.example.currencyapp.api.request

import com.example.currencyapp.BaseApplication
import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.api.response.InternalCurrency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetCurrencies(
    private val onSuccess: (Currency?) -> Unit,
    private val onError: (Throwable) -> Unit
) {

    @Inject
    lateinit var client: CurrencyClient

    init {
        BaseApplication.getComponent()?.inject(this)
    }

}