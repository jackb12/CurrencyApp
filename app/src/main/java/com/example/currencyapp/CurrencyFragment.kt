package com.example.currencyapp

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

class CurrencyFragment : Fragment() {

    lateinit var viewModel: CurrencyViewModel

    @BindView(R.id.currency_recycler_view)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.loading_view)
    lateinit var loadingView: View

    @BindView(R.id.error_view)
    lateinit var errorView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this)[CurrencyViewModel::class.java]
        viewModel.getUsers().observe(viewLifecycleOwner, Observer<List<Currency>>{ currencies ->
            // update UI
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // todo load currencies
    }


    private fun onLoaded() {}

    private fun onLoading() {}

    private fun onLoadingFailed() {}
}