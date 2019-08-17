package com.example.currencyapp.api.request

import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.api.network.NetworkClient
import com.example.currencyapp.api.response.InternalCurrency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCurrencies(
    private val base: String
) {

    private var client: CurrencyClient? = null

    fun getCurrencies(
        onSuccess: (Currency?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        if (client == null) {
            val retrofit = NetworkClient.retrofitClient
            client = retrofit.create(CurrencyClient::class.java)
        }

        client?.getCurrencies(base)?.enqueue(object: Callback<InternalCurrency> {
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