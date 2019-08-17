package com.example.currencyapp.model

import java.util.*
import kotlin.collections.HashMap

data class Currency(
    val base: String,
    val date: Date,
    val rates: HashMap<String, Int>
) {
    companion object {
        private const val serialVersionUID = -8440312718712063266L
    }
}