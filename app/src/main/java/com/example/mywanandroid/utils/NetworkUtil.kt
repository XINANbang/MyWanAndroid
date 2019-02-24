package com.example.mywanandroid.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * created by chenhanbin on 2019/2/22 14:35
 *
 */
 class NetworkUtil{
    companion object {

        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }

        fun isNetworkConnected(context: Context): Boolean{
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isConnected)
        }
    }
}