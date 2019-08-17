package com.example.currencyapp

import androidx.lifecycle.LiveData
import com.example.currencyapp.model.Currency
import kotlinx.coroutines.CoroutineScope

class CurrencyLiveData() : LiveData<List<Currency>>(), CoroutineScope {

}