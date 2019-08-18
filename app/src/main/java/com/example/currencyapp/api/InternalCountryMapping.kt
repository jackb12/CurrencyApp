package com.example.currencyapp.api

import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.response.InternalCountry

object InternalCountryMapping {

    fun mapCountries(response: List<InternalCountry>?) = response?.distinctBy {
        it.currencies?.firstOrNull()?.code
    }?.map {
        Country(
            it.currencies?.firstOrNull()?.code,
            it.currencies?.firstOrNull()?.name,
            it.flag
        )
    }
}