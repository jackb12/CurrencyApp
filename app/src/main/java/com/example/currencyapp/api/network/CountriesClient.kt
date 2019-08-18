package com.example.currencyapp.api.network

import com.example.currencyapp.api.response.InternalCountry
import retrofit2.Call
import retrofit2.http.GET

interface CountriesClient {

    @GET("rest/v2/all?fields=currencies;flag")
    fun getCountries(): Call<List<InternalCountry>>
}