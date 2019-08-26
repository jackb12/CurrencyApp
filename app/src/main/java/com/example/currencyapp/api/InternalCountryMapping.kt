package com.example.currencyapp.api

import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.response.InternalCountry

object InternalCountryMapping {

    const val EUR = "EUR"

    fun mapCountries(response: List<InternalCountry>?) = response?.distinctBy {
        it.currencies?.firstOrNull()?.code
    }?.map {
        Country(
            it.currencies?.firstOrNull()?.code,
            it.currencies?.firstOrNull()?.name,
            if (it.currencies?.firstOrNull()?.code == EUR) {
                // bad hack to use EU flag instead of country flag
                EUR
            } else {
                it.flag
            }
        )
    }

}