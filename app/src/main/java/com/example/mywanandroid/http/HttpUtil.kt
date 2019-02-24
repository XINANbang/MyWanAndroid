package com.example.mywanandroid.http

import android.util.Log
import com.example.mywanandroid.App
import com.example.mywanandroid.BuildConfig
import com.example.mywanandroid.api.ApiService
import com.example.mywanandroid.constant.HttpConstant
import com.example.mywanandroid.http.intercepter.CacheInterceptor
import com.example.mywanandroid.http.intercepter.HeadInterceptor
import com.example.mywanandroid.http.intercepter.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * create by chenhanbin at 2019/2/23
 **/
object HttpUtil {
    val TAG = "chenhanbin_net"
    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java) }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(HttpConstant.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val cacheFile = File(App.context.cacheDir, "cache")
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

        val httpLoggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger {
                message ->  kotlin.run {
                    Log.d(TAG, message)
            }
        })
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient().newBuilder()
        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeadInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            cache(cache)
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }

        return builder.build()
    }
}