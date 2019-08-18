package com.example.currencyapp.api.model

import java.io.Serializable

class Country(
    val code: String? = "",
    val name: String? = "",
    val flag: String? = ""
) : Serializable {
    companion object {
        private const val serialVersionUID = -7719778076238507493L
    }
}
