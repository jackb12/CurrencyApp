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

    fun getCurrencies(base: String) {
        client.getCurrencies(base).enqueue(object : Callback<InternalCurrency> {
            override fun onFailure(call: Call<InternalCurrency>, throwable: Throwable) {
                onError(throwable)
            }

            override fun onResponse(call: Call<InternalCurrency>, response: Response<InternalCurrency>) {
                if (response.isSuccessful) {
                    onSuccess(InternalCurrencyMapping.mapCurrency(response.body()))
                }
            }
        })
    }
}