package com.example.currencyapp.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.Resource
import com.example.currencyapp.api.model.Currency
import com.example.currencyapp.api.request.GetCurrencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyLiveData : LiveData<Resource<Currency>>() {

    fun getCurrencies(base: String) = GlobalScope.launch {
        val liveData = MutableLiveData<Resource<Currency>>()
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