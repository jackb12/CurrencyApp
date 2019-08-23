package com.example.currencyapp.repository

import android.util.Log
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CountriesClient
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.api.response.InternalCurrency
import com.example.currencyapp.livedata.CountryLiveData
import com.example.currencyapp.livedata.CurrencyLiveData
import com.example.currencyapp.room.CurrencyDao
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository {

    @Inject
    lateinit var currencyDao: CurrencyDao

    @Inject
    lateinit var currencyClient: CurrencyClient

    @Inject
    lateinit var countriesClient: CountriesClient

//    val currencyRateLiveData: LiveData<CurrencyRate>

    private val currencyLiveData = CurrencyLiveData()
    private val countryLiveData = CountryLiveData()

    init {
        BaseApplication.getComponent()?.inject(this)

    }

    fun fetchAll(base: String) = GlobalScope.launch(Dispatchers.Default) {
        val currencyRatesFromDatabase = currencyDao.getAll()
        val currencyRatesFromApi = getCurrencyRates(base)

//        currencyRatesFromApi.data?.map {
//            Log.e("REPOSITORY", "$it")
//        }

    }


    private fun getCurrencyRates(base: String) = GlobalScope.launch(Dispatchers.Default) {

        try {
            val currency = getCurrencyAsync(base)
            val countries = getCountries()
            if (currency != null) {
                Log.e("TEST", "$currency")
            } else {
                Log.e("TEST", "2")
            }
        } catch (e: Exception) {
            Log.e("TEST", "3")
        }
//            runBlocking {
//                currencyLiveData.getCurrencies(base)
//                countryLiveData.getCountries()
//            }

//          Resource.error(Throwable(""))
        }


    private suspend fun getCurrencyAsync(base: String): Currency? = InternalCurrencyMapping.mapCurrency(currencyClient.getCurrenciesAsync(base))

    private suspend fun getCountries() = countriesClient.getCountries()
}