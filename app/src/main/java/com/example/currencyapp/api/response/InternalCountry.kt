package com.example.currencyapp.api.response

data class InternalCountry(
    val currencies: List<InternalCountryCurrency>? = emptyList(),
    val flag: String? = ""
)