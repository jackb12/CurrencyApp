package com.example.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.livedata.CountryLiveData
import com.example.currencyapp.livedata.CurrencyLiveData

class CurrencyViewModel : ViewModel() {

    private val _currencyLiveData = CurrencyLiveData()
    private val _countriesLiveData = CountryLiveData()

    val currencyLiveData: LiveData<Resource<Currency>>
        get() = _currencyLiveData

    val countriesLiveData: LiveData<Resource<List<Country>>>
        get() = _countriesLiveData

    fun getCurrencies(base: String) = _currencyLiveData.getCurrencies(base)


    fun getCountries() = _countriesLiveData.getCountries()
}