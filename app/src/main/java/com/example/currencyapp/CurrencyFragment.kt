package com.example.currencyapp

import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.api.model.Currency

class CurrencyFragment : Fragment() {

    lateinit var viewModel: CurrencyViewModel

    @BindView(R.id.currency_recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.loading_view)
    lateinit var loadingView: View

    @BindView(R.id.error_view)
    lateinit var errorView: View

    companion object {
        private const val EUR = "EUR"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[CurrencyViewModel::class.java]
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currencyLiveData.observe(viewLifecycleOwner, Observer<Resource<Currency>> { resource ->
            when (resource.status) {
                SUCCESS -> {
                    resource.data?.let {
                        onLoaded(it)
                    }
                }
                ERROR -> onLoadingFailed()
            }
        })

        viewModel.getCurrencies(EUR)
    }


    private fun onLoaded(it: Currency) {
        Log.e("CURRENCY", "$it")
    }

    private fun onLoading() {}

    private fun onLoadingFailed() {}
}