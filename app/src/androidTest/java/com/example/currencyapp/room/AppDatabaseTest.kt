package com.example.currencyapp.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {

    private lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()
    }


    @After
    fun tearDown() {
        appDatabase.close()
    }


    @Test
    fun insertCurrencyData() {
        val currencyRates = listOf(
            CurrencyRate(
                currencyCode = "EUR",
                currencyDescription = "euro",
                currencyFlag = "flag",
                currencyRate = 1.0F
            )
        )

        appDatabase.currencyDao().insertAll(currencyRates)

        val dbResult = appDatabase.currencyDao().getAll()
        assert(dbResult.size == 1)
        assertEquals("EUR", dbResult[0].currencyCode)
        assertEquals("euro", dbResult[0].currencyDescription)
        assertEquals("EUR", dbResult[0].currencyFlag)
        assertEquals(1.0F, dbResult[0].currencyRate)
    }
}