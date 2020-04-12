package com.ganesh.currency.utill

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by ganeshkumarraja on 4/10/20.
 */
@Suppress("DEPRECATION")
class Internet {

    companion object {
        const val errorMessage = "No Internet Connection"

        fun isAvailable(context: Context): Boolean {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            cm?.apply {
                val netInfo = activeNetworkInfo

                return netInfo != null && netInfo.isConnectedOrConnecting
            }

            return false
        }
    }
}
