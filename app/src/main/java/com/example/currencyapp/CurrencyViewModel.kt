package com.example.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.api.model.Currency

class CurrencyViewModel : ViewModel() {

    private val _currencyLiveData = CurrencyLiveData()

    val currencyLiveData: LiveData<Resource<Currency>>
        get() = _currencyLiveData


    fun getCurrencies(base: String) = _currencyLiveData.getCurrencies(base)
}