package ar.com.tallerdeprogramacion.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkControl {
    fun isActiveNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}