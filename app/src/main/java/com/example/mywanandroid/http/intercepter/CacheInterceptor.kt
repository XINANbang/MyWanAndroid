package com.example.mywanandroid.http.intercepter

import com.example.mywanandroid.App
import com.example.mywanandroid.constant.HttpConstant
import com.example.mywanandroid.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * create by chenhanbin at 2019/2/23
 **/
class CacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtil.isNetworkAvailable(App.context)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }

        val response = chain.proceed(request)
        if (!NetworkUtil.isNetworkAvailable(App.context)) {
            val maxAge = 60 * 3
            response.newBuilder()
                .header(HttpConstant.CACHE_CONTROL, "public, max-age=$maxAge")
                .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 //4周超时
            response.newBuilder()
                .header(HttpConstant.CACHE_CONTROL, "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("nyn")
                .build()
        }
        return response
    }

}