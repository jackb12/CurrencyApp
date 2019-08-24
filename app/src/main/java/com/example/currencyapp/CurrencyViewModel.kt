package com.example.currencyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.room.CurrencyRate

class CurrencyViewModel : ViewModel() {

    var repository: CurrencyRepository
    var base: String = DEFAULT_BASE

    val currencies: LiveData<Resource<List<CurrencyRate>>>
        get() = repository.currencyRates

    companion object {
        private const val DEFAULT_BASE = "EUR"
    }

    init {
        BaseApplication.getComponent()?.inject(this)
        repository = CurrencyRepository()
        repository.fetchAll(DEFAULT_BASE)
    }


    fun getLatestRates(base: String) {
        this.base = base
        repository.fetchCurrencies(base)
    }
}