package com.sopian.mygithub.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HasInternetCapability @Inject constructor(@ApplicationContext private val context: Context) {
     val isConnected: Boolean
     get() {
         val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             val nw = cm.activeNetwork ?: return false
             val actNw = cm.getNetworkCapabilities(nw) ?: return false
             return when {
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                 actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                 else -> false
             }
         } else {
             val nwInfo = cm.activeNetworkInfo ?: return false
             return nwInfo.isConnected
         }
     }
}