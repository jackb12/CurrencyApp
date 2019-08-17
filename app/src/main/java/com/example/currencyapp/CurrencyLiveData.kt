package com.example.currencyapp

import androidx.lifecycle.LiveData
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.request.GetCurrencies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyLiveData() : LiveData<Resource<Currency>>() {

    fun getCurrencies(base: String) = GlobalScope.launch(Dispatchers.Default) {
        GetCurrencies(
            {
                postValue(Resource.success(it))
            },
            {
                postValue(Resource.error(it))
            }
        ).getCurrencies(base)
    }

}