package com.sopian.mygithub.core.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel() {
    private var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val _hasConnection = MutableLiveData(false)
    val hasConnection = _hasConnection

    init {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onCleared() {
        super.onCleared()
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d("onAvailable", network.toString())
            val networkCapabilities = cm.getNetworkCapabilities(network)
            if (networkCapabilities == null) _hasConnection.postValue(false)
            val hasInternetCapability = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            Log.d("onAvailable:", hasInternetCapability.toString())
            if (hasInternetCapability == true) {
                _hasConnection.postValue(true)
            }
        }

        override fun onLost(network: Network) {
            Log.d("onLost:", network.toString())
            _hasConnection.postValue(false)
        }
    }
}