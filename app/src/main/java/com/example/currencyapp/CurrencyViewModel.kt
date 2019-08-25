package com.example.currencyapp

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.room.CurrencyRate


class CurrencyViewModel : ViewModel() {

    private val repository: CurrencyRepository = CurrencyRepository()
    var base: String = DEFAULT_BASE

    private var shouldStopLoop = false
    private var isRunning = false
    private var handler = Handler()

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (currencies.value == null) {
                repository.fetchAll(base)
            } else {
                getLatestRates(base)
            }

            if (!shouldStopLoop) {
                handler.postDelayed(this, 1000)
            }
        }
    }

    val currencies: LiveData<Resource<List<CurrencyRate>>>
        get() = repository.currencyRates

    companion object {
        private const val DEFAULT_BASE = "EUR"
    }

    init {
        BaseApplication.getComponent()?.inject(this)
        repository.fetchAll(DEFAULT_BASE)
    }


    fun startRunnable() {
        if (!isRunning) {
            isRunning = true
            handler.post(runnable)
        }
    }


    fun stopRunnable() {
        isRunning = false
        handler.removeCallbacks(runnable)
    }


    fun getLatestRates(base: String) {
        this.base = base
        repository.fetchCurrencies(base)
    }
}