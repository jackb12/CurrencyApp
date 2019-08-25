package com.example.currencyapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager


class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = checkConnection(context)

        if (connectionListener != null) {
            connectionListener?.onNetworkConnectionChange(isConnected)
        }
    }


    interface ConnectionListener {
        fun onNetworkConnectionChange(isConnected: Boolean)
    }


    companion object {

        var connectionListener: ConnectionListener? = null

        val isConnected: Boolean
        get() {
            val connectivityManager = BaseApplication.getInstance()?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo

            return (activeNetwork != null && activeNetwork.isConnected)
        }
     }

    private fun checkConnection(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo

        return (activeNetwork != null && activeNetwork.isConnected)
    }


}