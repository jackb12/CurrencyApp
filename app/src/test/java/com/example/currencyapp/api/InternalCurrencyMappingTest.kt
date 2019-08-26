package com.example.currencyapp.api

import com.example.currencyapp.api.response.InternalCurrency
import org.junit.Assert.*
import org.junit.Test

class InternalCurrencyMappingTest {

    @Test
    fun testCurrencyMapping() {
        val internalCurrency = InternalCurrency(
            base = "EUR",
            rates = hashMapOf("BGN" to 1.234F, "AUD" to 1.678F)
        )

        val currency = InternalCurrencyMapping.mapCurrency(internalCurrency)

        assert(currency != null)
        assertEquals("EUR", currency?.base)
        assertEquals(1.234F, currency?.rates?.get("BGN"))
    }
}