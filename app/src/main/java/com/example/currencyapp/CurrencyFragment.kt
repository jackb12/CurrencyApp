package com.example.currencyapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.currencyapp.Resource.Companion.ERROR
import com.example.currencyapp.Resource.Companion.LOADING
import com.example.currencyapp.Resource.Companion.SUCCESS
import com.example.currencyapp.room.CurrencyRate
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_currency.*

class CurrencyFragment : Fragment(), NetworkReceiver.ConnectionListener {

    private lateinit var viewModel: CurrencyViewModel
    private var snackbar: Snackbar? = null


    override fun onNetworkConnectionChange(isConnected: Boolean) {
        if (isConnected) {
            viewModel.startRunnable()
        } else {
            viewModel.stopRunnable()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[CurrencyViewModel::class.java]
        activity?.baseContext?.registerReceiver(
            NetworkReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        BaseApplication.getInstance()?.setConnectionListener(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        snackbar = requireActivity().findViewById<View>(R.id.main_activity).let {
            Snackbar.make(
                it,
                getString(R.string.currency_connection_error),
                Snackbar.LENGTH_INDEFINITE
            )
        }
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
                        updateAdapter(it)
                        snackbar?.dismiss()
                        viewModel.startRunnable()
                        onLoaded(true)
                    } ?: onLoaded(false)
                }
                LOADING -> {
                    onLoading()
                }
                ERROR -> {
                    if (resource.data?.isNullOrEmpty() == false) {
                        updateAdapter(resource.data)
                        snackbar?.show()
                        onLoaded(true)
                    } else {
                        onLoadingFailed()
                    }

                    viewModel.stopRunnable()
                }
            }
        })
    }


    private fun updateAdapter(currencies: List<CurrencyRate>) {
        (currencyRecyclerView.adapter as CurrencyAdapter).setItems(currencies)
    }


    private fun getCurrencyRate(base: String) {
        viewModel.getLatestRates(base)
    }


    private fun onLoaded(hasItems: Boolean) {
        if (hasItems) {
            currencyRecyclerView.visibility = VISIBLE
            errorView.visibility = GONE
            loadingView.visibility = GONE
        } else {
            currencyRecyclerView.visibility = GONE
            errorView.visibility = VISIBLE
            loadingView.visibility = GONE
        }
    }


    private fun onLoading() {
        currencyRecyclerView.visibility = GONE
        errorView.visibility = GONE
        loadingView.visibility = VISIBLE
    }


    private fun onLoadingFailed() {
        currencyRecyclerView.visibility = GONE
        errorView.visibility = VISIBLE
        loadingView.visibility = GONE
    }
}