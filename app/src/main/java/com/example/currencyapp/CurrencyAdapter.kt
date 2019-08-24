package com.example.currencyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.room.CurrencyRate

class CurrencyAdapter(
    private val onFocusChanged: (String) -> Unit
): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var currencies: MutableList<CurrencyRate> = mutableListOf()

    private var baseAmount: Float = DEFAULT_BASE_AMOUNT

    companion object {
        const val FIRST_INDEX = 0
        const val DEFAULT_BASE_AMOUNT = 100F
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_currency, parent, false),
            { currencyRate ->
                onFocus(currencyRate)
            },
            {}
        )


    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.onBind(currencies[position], baseAmount)
    }


    override fun getItemCount(): Int = currencies.size


    fun setItems(currencies: List<CurrencyRate>) {
        this.currencies = currencies.toMutableList()
        notifyDataSetChanged()
    }


    private fun onFocus(currencyRate: CurrencyRate) {
        baseAmount *= currencyRate.currencyRate
        val focusedPosition = currencies.indexOf(currencyRate)
        currencies.remove(currencyRate)
        currencies.add(FIRST_INDEX, currencyRate)
        notifyItemMoved(focusedPosition, FIRST_INDEX)
        onFocusChanged(currencyRate.currencyCode)
    }
}