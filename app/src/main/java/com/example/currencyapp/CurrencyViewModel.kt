package com.example.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.livedata.CountryLiveData
import com.example.currencyapp.livedata.CurrencyLiveData
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.room.CurrencyRate

class CurrencyViewModel : ViewModel() {

    private val repository: CurrencyRepository
//    val currencies: LiveData<Resource<List<CurrencyRate>>>

    init {
        BaseApplication.getComponent()?.inject(this)
        repository = CurrencyRepository()
        repository.fetchAll("EUR")
    }

    private val _currencyLiveData = CurrencyLiveData()
    private val _countriesLiveData = CountryLiveData()

    val currencyLiveData: LiveData<Resource<Currency>>
        get() = _currencyLiveData

    val countriesLiveData: LiveData<Resource<List<Country>>>
        get() = _countriesLiveData


    fun fetchAll(base: String) = repository.fetchAll(base)


    fun getCurrencies(base: String) = _currencyLiveData.getCurrencies(base)


    fun getCountries() = _countriesLiveData.getCountries()
}