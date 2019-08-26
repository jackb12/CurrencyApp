package com.example.currencyapp.api

import com.example.currencyapp.api.response.InternalCountry
import com.example.currencyapp.api.response.InternalCountryCurrency
import org.junit.Assert.assertEquals
import org.junit.Test

class InternalCountryMappingTest {

    @Test
    fun testCountryMapping() {
        val internalCountries = listOf(
            InternalCountry(
                currencies = listOf(
                    InternalCountryCurrency(
                        "EUR",
                        "Euro"
                    )
                ),
                flag = "flag"
            ),
            InternalCountry(
                currencies = listOf(
                    InternalCountryCurrency(
                        "USD",
                        "United States Dollar"
                    )
                ),
                flag = "usa-flag"
            )
        )

        val countries = InternalCountryMapping.mapCountries(internalCountries)

        assert(countries?.isNotEmpty() == true)
        assertEquals("EUR", countries?.get(0)?.code)
        assertEquals("Euro", countries?.get(0)?.name)
        assertEquals("EUR", countries?.get(0)?.flag)

        assertEquals("USD", countries?.get(1)?.code)
        assertEquals("United States Dollar", countries?.get(1)?.name)
        assertEquals("usa-flag", countries?.get(1)?.flag)
    }
}