package com.example.currencyapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.currencyapp.CurrencyConverter.getAmount
import com.example.currencyapp.room.CurrencyRate

class CurrencyViewHolder(
    itemVIew: View,
    private val onAmountFocus: (CurrencyRate) -> Unit,
    private val onAmountChanged: (Float) -> Unit
) : RecyclerView.ViewHolder(itemVIew) {

    private val currencyCountryImage = itemVIew.findViewById<ImageView>(R.id.currency_country_image)
    private val currencyCode = itemVIew.findViewById<TextView>(R.id.currency_country_code)
    private val currencyDescription = itemVIew.findViewById<TextView>(R.id.currency_description)
    private val currencyAmount = itemVIew.findViewById<TextView>(R.id.currency_amount)

    fun onBind(currency: CurrencyRate, baseAmount: Float) = itemView.apply {
        Glide
            .with(currencyCountryImage)
            .load(currency.currencyFlag)
            .centerCrop()
            .placeholder(R.color.textColourGrey)
            .into(currencyCountryImage)

        currencyCode.text = currency.currencyCode
        currencyDescription.text = currency.currencyDescription
        currencyAmount.text = getAmount(currency.currencyRate, baseAmount).toString()

        currencyAmount.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                onAmountFocus(currency)
            }
        }
    }
}