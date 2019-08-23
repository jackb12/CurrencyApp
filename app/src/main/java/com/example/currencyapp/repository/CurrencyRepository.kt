package com.example.currencyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.Resource
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.api.response.InternalCurrency
import com.example.currencyapp.livedata.CountryLiveData
import com.example.currencyapp.livedata.CurrencyLiveData
import com.example.currencyapp.room.CurrencyDao
import com.example.currencyapp.room.CurrencyRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository {

    @Inject
    lateinit var currencyDao: CurrencyDao

    @Inject
    lateinit var client: CurrencyClient

//    val currencyRateLiveData: LiveData<CurrencyRate>

    private val currencyLiveData = CurrencyLiveData()
    private val countryLiveData = CountryLiveData()

    init {
        BaseApplication.getComponent()?.inject(this)

    }

    fun fetchAll(base: String) = GlobalScope.launch(Dispatchers.Default) {
        val currencyRatesFromDatabase = currencyDao.getAll()
        val currencyRatesFromApi = getCurrencyRates(base)

        currencyRatesFromApi.data?.map {
            Log.e("REPOSITORY", "$it")
        }

    }


    private fun getCurrencyRates(base: String): Resource<List<CurrencyRate>> = runBlocking {
        val currency = t(base)
//        runBlocking {
//
//            countryLiveData.getCountries()
//        }

        if (currency.value != null) {

            val result: List<CurrencyRate>? = currencyLiveData.value?.data?.rates?.mapNotNull { rate ->
                val country = countryLiveData.value?.data?.firstOrNull {
                    it.code == rate.key
                }

                if (country != null) {
                    CurrencyRate(
                        rate.key,
                        country.name,
                        country.flag,
                        rate.value
                    )
                } else {
                    null
                }
            }

            Log.e("TEST", "1")
            Resource.success(result)
        } else {
            Log.e("TEST", "2")
            Resource.error(Throwable())
        }
    }


    private fun t(base: String): LiveData<Currency?> = liveData(Dispatchers.IO) {
        val c = InternalCurrencyMapping.mapCurrency(getCurrencies(base))

        emit(c)
    }

    private suspend fun getCurrencies(base: String) = client.getCurrencies(base)

}