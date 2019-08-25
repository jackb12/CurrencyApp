package com.example.currencyapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.LOADING
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.model.Currency
import kotlinx.android.synthetic.main.fragment_currency.*

class CurrencyFragment : Fragment() {

    private lateinit var viewModel: CurrencyViewModel

    companion object {
        private const val EUR = "EUR"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[CurrencyViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyRecyclerView.itemAnimator = null
        currencyRecyclerView.adapter = CurrencyAdapter(
            { base ->
                getCurrencyRate(base)
            },
            {
                currencyRecyclerView.post {
                    (currencyRecyclerView.adapter as CurrencyAdapter).notifyItemRangeChanged(
                        CurrencyAdapter.FIRST_INDEX + 1,
                        (currencyRecyclerView.adapter as CurrencyAdapter).itemCount
                    )
                }
            }
        )

        viewModel.currencies.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                SUCCESS -> {
                    resource.data?.let {
                        (currencyRecyclerView.adapter as CurrencyAdapter).setItems(
                            it
                        )
                    }

                    viewModel.startRunnable()
                }
                LOADING -> {
                    onLoading()
                }
                ERROR -> {
                    onLoadingFailed()
                    viewModel.stopRunnable()
                }
            }
        })
    }


    private fun getCurrencyRate(base: String) {
        Log.e("CURRENCY", base)
        viewModel.getLatestRates(base)
    }


    private fun onLoaded(currency: Currency) {

    }


    private fun onLoading() {}


    private fun onLoadingFailed() {}
}