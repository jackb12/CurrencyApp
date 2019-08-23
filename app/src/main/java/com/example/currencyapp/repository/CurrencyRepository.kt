package com.example.currencyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.currencyapp.BaseApplication
import com.example.currencyapp.Resource
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.model.Currency
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


    private fun getCurrencyRates(base: String): Resource<List<CurrencyRate>> {
        val mediator = MediatorLiveData<Pair<Currency?, List<Country>>?>().apply {
            var lastA: Currency? = null
            var lastB: List<Country>? = null

            fun update() {
                val localLastA = lastA
                val localLastB = lastB
                if (localLastA != null && localLastB != null)
                    this.value = Pair(localLastA, localLastB)
            }

            addSource(currencyLiveData) {
                if (it.status == SUCCESS || it.status == ERROR) {
                    lastA = it.data
                    update()
                }
            }
            addSource(countryLiveData) {
                if (it.status == SUCCESS || it.status == ERROR) {
                    lastB = it.data
                    update()
                }
            }
        }

        runBlocking {
            currencyLiveData.getCurrencies(base)
            countryLiveData.getCountries()
        }

        return if (mediator.value?.first != null && mediator.value?.second != null) {

            val result: List<CurrencyRate>? = mediator.value?.first?.rates?.mapNotNull { rate ->
                val country = mediator.value?.second?.firstOrNull {
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

            Resource.success(result)
        } else {
            Resource.error(Throwable())
        }
    }
}