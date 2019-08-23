package com.example.currencyapp.livedata

import androidx.lifecycle.LiveData
import com.example.currencyapp.Resource
import com.example.currencyapp.api.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryLiveData : LiveData<Resource<List<Country>>>() {

}