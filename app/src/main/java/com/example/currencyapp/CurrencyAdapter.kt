package com.example.currencyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.room.CurrencyRate
import android.text.Editable
import android.text.TextWatcher


class CurrencyAdapter(
    private val onFocusChanged: (String) -> Unit,
    private val onLostFocus: (Float) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    var currencies: MutableList<CurrencyRate> = mutableListOf()

    private var baseAmount: Float = DEFAULT_BASE_AMOUNT
    private var onChange = false

    companion object {
        const val FIRST_INDEX = 0
        const val DEFAULT_BASE_AMOUNT = 100F
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_holder_currency,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        holder.onBind(currency, baseAmount)

        holder.currencyAmount.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && view.isFocused && !onChange && position != FIRST_INDEX) {
                onFocus(currency)
            }
        }

        holder.currencyAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(amount: Editable?) {
                if (holder.currencyAmount.isFocused) {
                    baseAmount = amount.toString().toFloat()
                    onLoseFocus(
                        CurrencyConverter.getAmount(
                            CurrencyConverter.getNumericalAmount(
                                amount.toString()
                            ), currency.currencyRate
                        )
                    )
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }


    override fun getItemCount(): Int = currencies.size


    fun setItems(currencies: List<CurrencyRate>) {
        if (this.currencies.isNullOrEmpty()) {
            this.currencies = currencies.toMutableList()
            notifyDataSetChanged()
        } else {
            updateItems(currencies)
        }
    }


    private fun updateItems(currencies: List<CurrencyRate>) {
        this.currencies = (this.currencies).map { currentCurrencyRate ->
            val updatedCurrency = currencies.first { it.currencyCode == currentCurrencyRate.currencyCode }
            currentCurrencyRate.copy(currencyRate = updatedCurrency.currencyRate)
        }.toMutableList()

        onChange = false

        notifyDataSetChanged()
    }


    private fun onFocus(currency: CurrencyRate) {
        onChange = true
        baseAmount *= currency.currencyRate
        val focusedPosition = currencies.indexOf(currency)
        currencies.remove(currency)
        currencies.add(FIRST_INDEX, currency)
        notifyItemMoved(focusedPosition, FIRST_INDEX)
        onFocusChanged(currency.currencyCode)
    }


    private fun onLoseFocus(amount: Float) {
        this.baseAmount = amount
        onLostFocus(amount)
    }
}