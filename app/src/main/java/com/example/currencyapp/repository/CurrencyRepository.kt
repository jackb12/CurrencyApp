package com.example.currencyapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.R
import com.example.currencyapp.Resource
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.InternalCountryMapping
import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.network.CountriesClient
import com.example.currencyapp.api.network.CurrencyClient
import com.example.currencyapp.room.CurrencyDao
import com.example.currencyapp.room.CurrencyRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    var currencyRates = MutableLiveData<Resource<List<CurrencyRate>>>()

    init {
        BaseApplication.getComponent()?.inject(this)

    }

    fun fetchAll(base: String) = GlobalScope.launch(Dispatchers.Default) {
        currencyRates.postValue(Resource.loading())
        val currencyRatesFromDatabase = currencyDao.getAll()
        val currencyRatesFromApi: Resource<List<CurrencyRate>> = try {
            getCurrencyRates(base)
        } catch (e: Exception) {
            Log.e("TEST", "3")
            Resource.error(Throwable(""))
        }

        when(currencyRatesFromApi.status) {
            SUCCESS -> {
                currencyRatesFromApi.data?.let {
                    currencyDao.insertAll(it)
                    currencyRates.postValue(Resource.success(it))
                }
            }
            ERROR -> {
                if (currencyRatesFromDatabase.isNullOrEmpty()) {
                    currencyRatesFromApi.error?.let {
                        currencyRates.postValue(Resource.error(it))
                    }
                } else {
                    currencyRates.postValue(Resource.errorWithPayload(currencyRatesFromDatabase))
                }
            }
            else -> {

            }
        }
    }


    private suspend fun getCurrencyRates(base: String): Resource<List<CurrencyRate>> {
        val currencies = getCurrencyAsync(base)
        val countries = getCountries()
        return if (currencies != null && !countries.isNullOrEmpty()) {
            Resource.success(currencies.rates.map { currency ->
                val country = countries.firstOrNull { it.code == currency.key }

                CurrencyRate(
                    currency.key,
                    country?.name,
                    country?.flag,
                    currency.value
                )
            })
        } else {
            Resource.error(Throwable(BaseApplication.getInstance()?.applicationContext?.getString(R.string.currency_error)))
        }
    }


    private suspend fun getCurrencyAsync(base: String): Currency? =
        InternalCurrencyMapping.mapCurrency(currencyClient.getCurrenciesAsync(base))


    private suspend fun getCountries() =
        InternalCountryMapping.mapCountries(countriesClient.getCountries())
}