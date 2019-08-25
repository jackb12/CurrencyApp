package com.example.currencyapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.R
import com.example.currencyapp.Resource
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.InternalCountryMapping
import com.example.currencyapp.api.InternalCurrencyMapping
import com.example.currencyapp.api.model.Country
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
            getDefaultError()
        }

        when (currencyRatesFromApi.status) {
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


    fun fetchCurrencies(base: String) = GlobalScope.launch(Dispatchers.Default) {
        val updatedCurrencies = getUpdatedCurrencyRates(base)
        when (updatedCurrencies.status) {
            SUCCESS -> {
                updatedCurrencies.data?.let {
                    currencyDao.updateCurrencies(it)
                    currencyRates.postValue(Resource.success(currencyDao.getAll()))
                }
            }
            ERROR -> {

            }
            else -> {

            }
        }
    }


    private suspend fun getUpdatedCurrencyRates(base: String): Resource<List<CurrencyRate>> {
        val currencyRatesFromDatabase = currencyDao.getAll()
        val currenciesFromApi = getCurrencyAsync(base)

        return if (currenciesFromApi != null) {
            val updatedCurrencies = currencyRatesFromDatabase.mapNotNull { currencyRate ->
                findCurrency(base, currencyRate.currencyCode, currenciesFromApi.rates[currencyRate.currencyCode]).let {
                    if (it.second != null && currencyRate.currencyRate != it.second) {
                        currencyRate.copy(currencyRate = it.second!!)
                    } else {
                        null
                    }
                }

            }

            Resource.success(updatedCurrencies)
        } else {
            getDefaultError()
        }
    }


    private fun findCurrency(
        base: String,
        currencyCode: String,
        updatedCurrencyRate: Float?
    ): Pair<String, Float?> {
        return if (currencyCode == base) {
            Pair(currencyCode, 1.0F)
        } else {
            Pair(currencyCode, updatedCurrencyRate)
        }
    }


    private suspend fun getCurrencyRates(base: String): Resource<List<CurrencyRate>> {
        val currencies = getCurrencyAsync(base)
        val countries = getCountries()
        return if (currencies != null && !countries.isNullOrEmpty()) {
            Resource.success(mapRates(currencies, countries))
        } else {
            getDefaultError()
        }
    }

    private fun mapRates(
        currencies: Currency,
        countries: List<Country>
    ): List<CurrencyRate> = ArrayList<CurrencyRate>().apply {
        val baseCountry = countries.firstOrNull { it.code == currencies.base }

        add(
            CurrencyRate(
                currencies.base,
                baseCountry?.name,
                baseCountry?.flag,
                1.0f
            )
        )

        addAll(currencies.rates.map { currency ->
            val country = countries.firstOrNull { it.code == currency.key }

            CurrencyRate(
                currency.key,
                country?.name,
                country?.flag,
                currency.value
            )
        })
    }.toList()


    private suspend fun getCurrencyAsync(base: String): Currency? =
        InternalCurrencyMapping.mapCurrency(currencyClient.getCurrenciesAsync(base))


    private suspend fun getCountries() =
        InternalCountryMapping.mapCountries(countriesClient.getCountries())


    private fun getDefaultError() =
        Resource.error<List<CurrencyRate>>(
            Throwable(
                BaseApplication.getInstance()?.applicationContext?.getString(
                    R.string.currency_connection_error
                )
            )
        )

}