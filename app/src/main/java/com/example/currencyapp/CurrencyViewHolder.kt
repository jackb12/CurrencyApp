package com.example.currencyapp

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.CurrencyConverter.getFormattedAmount
import com.example.currencyapp.api.InternalCountryMapping.EUR
import com.example.currencyapp.glide.GlideModule
import com.example.currencyapp.room.CurrencyRate
import java.util.*

class CurrencyViewHolder(
    itemVIew: View
) : RecyclerView.ViewHolder(itemVIew) {

    private val currencyCountryImage = itemVIew.findViewById<ImageView>(R.id.currency_country_image)
    private val currencyCode = itemVIew.findViewById<TextView>(R.id.currency_country_code)
    private val currencyDescription = itemVIew.findViewById<TextView>(R.id.currency_description)
    val currencyAmount: EditText = itemVIew.findViewById(R.id.currency_amount)

    fun onBind(currency: CurrencyRate, baseAmount: Float) = itemView.apply {
        // TODO fix glide SVG issue
//        if (currency.currencyCode == EUR) {
//            GlideModule.loadImage(
//                context,
//                currencyCountryImage,
//                context.getDrawable(R.drawable.ic_flag_eu)!!
//            )
//        } else {
//            currency.currencyFlag?.let {
//                GlideModule.loadImage(context, currencyCountryImage, it)
//            }
//        }

        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        currencyCountryImage.setBackgroundColor(color)
        currencyCode.text = currency.currencyCode
        currencyDescription.text = currency.currencyDescription
        currencyAmount.setText(getFormattedAmount(currency.currencyRate, baseAmount))
    }


}