package com.example.currencyapp.api.request

import com.example.currencyapp.BaseApplication
import com.example.currencyapp.api.InternalCountryMapping
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.network.CountriesClient
import com.example.currencyapp.api.response.InternalCountry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetCountries(
    private val onSuccess: (List<Country>?) -> Unit,
    private val onError: (Throwable) -> Unit
) {

    @Inject
    lateinit var client: CountriesClient

    init {
        BaseApplication.getComponent()?.inject(this)
    }

    fun getCountries() {
        client.getCountries().enqueue(object : Callback<List<InternalCountry>> {
            override fun onFailure(call: Call<List<InternalCountry>>, throwable: Throwable) {
                onError(throwable)
            }

            override fun onResponse(call: Call<List<InternalCountry>>, response: Response<List<InternalCountry>>) {
                if (response.isSuccessful) {
                onSuccess(InternalCountryMapping.mapCountries(response.body()))
                }
            }

        })
    }
}