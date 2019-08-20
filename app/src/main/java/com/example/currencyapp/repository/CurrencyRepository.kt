package com.example.currencyapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.Resource
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.api.request.GetCurrencies
import com.example.currencyapp.room.CurrencyDao
import com.example.currencyapp.room.CurrencyRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository {

    @Inject
    lateinit var currencyDao: CurrencyDao

    val currencyRatesLiveData: LiveData<CurrencyRates>

    init {
        BaseApplication.getComponent()?.inject(this)

    }

    fun fetchAll(base: String) = GlobalScope.launch(Dispatchers.Default) {
        val currencyRates = currencyDao.getAll()
        if (currencyRates.is)
    }


    // TODO load currencies and countries for the first time + add to db
    private suspend fun getCurrencies(base: String): LiveData<Resource<Currency>>  {
        val liveData = MutableLiveData<Resource<Currency>>()
        GetCurrencies(
            {

                liveData.value = (Resource.success(it))
            },
            {
                liveData.value = (Resource.error(it))
            }
        ).getCurrencies(base)

        return liveData
    }


    // update rates every second
}