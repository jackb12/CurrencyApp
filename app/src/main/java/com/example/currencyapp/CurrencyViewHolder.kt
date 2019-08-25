package com.example.currencyapp

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.currencyapp.CurrencyConverter.getAmount
import com.example.currencyapp.CurrencyConverter.getFormattedAmount
import com.example.currencyapp.CurrencyConverter.getNumericalAmount
import com.example.currencyapp.room.CurrencyRate

class CurrencyViewHolder(
    itemVIew: View
) : RecyclerView.ViewHolder(itemVIew) {

    private val currencyCountryImage = itemVIew.findViewById<ImageView>(R.id.currency_country_image)
    private val currencyCode = itemVIew.findViewById<TextView>(R.id.currency_country_code)
    private val currencyDescription = itemVIew.findViewById<TextView>(R.id.currency_description)
    val currencyAmount: EditText = itemVIew.findViewById(R.id.currency_amount)

    fun onBind(currency: CurrencyRate, baseAmount: Float) = itemView.apply {
        Glide
            .with(currencyCountryImage)
            .load(currency.currencyFlag)
            .centerCrop()
            .placeholder(R.color.textColourGrey)
            .into(currencyCountryImage)

        currencyCode.text = currency.currencyCode
        currencyDescription.text = currency.currencyDescription
        currencyAmount.setText(getFormattedAmount(currency.currencyRate, baseAmount))
    }


}