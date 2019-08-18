package com.example.currencyapp.livedata

import androidx.lifecycle.LiveData
import com.example.currencyapp.Resource
import com.example.currencyapp.api.model.Country
import com.example.currencyapp.api.request.GetCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryLiveData(): LiveData<Resource<List<Country>>>() {

    fun getCountries() = GlobalScope.launch(Dispatchers.Default) {
        GetCountries(
            {
                postValue(Resource.success(it))
            },
            {
                postValue(Resource.error(it))
            }
        ).getCountries()
    }
}