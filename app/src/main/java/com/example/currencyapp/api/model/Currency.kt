package com.example.currencyapp.api.model

data class Currency(
    val base: String,
    val rates: HashMap<String, Float>
) {
    companion object {
        private const val serialVersionUID = -8440312718712063266L
    }
}