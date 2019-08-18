package com.example.currencyapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CurrencyViewHolder(
    itemVIew: View,
    onAmountFocus: () -> Unit,
    onAmountChanged: (Float) -> Unit
) : RecyclerView.ViewHolder(itemVIew) {

    fun onBind(currency: Float?) = itemView.apply {

    }
}