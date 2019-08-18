package com.example.currencyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.api.model.Currency

class CurrencyAdapter: RecyclerView.Adapter<CurrencyViewHolder>() {

    private var currency: Currency? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_currency, parent, false), {}, {})


    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {

    }


    override fun getItemCount(): Int = currency?.rates?.size ?: 0


    fun setItems(currency: Currency) {
        this.currency = currency
    }
}