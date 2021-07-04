package com.example.workshopapp.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {
    companion object {

        const val BASE_URL = "https://bigvu-interviews-assets.s3.amazonaws.com/"
        const val WORKSHOP_API = "${BASE_URL}workshops.json"

        fun isInternetAvailable(context: Context): Boolean {
            val isAvailable: Boolean
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            isAvailable = cm.activeNetworkInfo != null
            return isAvailable
        }
    }
}