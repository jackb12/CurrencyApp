package com.example.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.room.CurrencyRate

class CurrencyViewModel : ViewModel() {

    var repository: CurrencyRepository

    val currencies: LiveData<Resource<List<CurrencyRate>>>
        get() = repository.currencyRates

    init {
        BaseApplication.getComponent()?.inject(this)
        repository = CurrencyRepository()
        repository.fetchAll("EUR")
    }


    fun getLatestRates() {}
}