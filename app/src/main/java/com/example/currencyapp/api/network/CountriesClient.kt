package com.example.currencyapp.api.network

import com.example.currencyapp.api.response.InternalCountry
import retrofit2.http.GET

interface CountriesClient {

    @GET("rest/v2/all?fields=currencies;flag")
    suspend fun getCountries(): List<InternalCountry>
}