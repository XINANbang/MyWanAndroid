package com.example.mywanandroid.rx.Exception

import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * create by chenhanbin at 2019/2/23
 **/
class ExceptionPares {
    companion object {
        private const val TAG = "chenhanbin_net_exc"
        var errorMsg: String = "请求失败，请稍后重试"
        fun pares(e: Throwable): String {
            when {
                e is SocketTimeoutException || e is ConnectException || e is HttpException || e is UnknownHostException -> {
                    Log.d(TAG, "网络链接异常" + e.message)
                    errorMsg = "网络链接异常"
                }
                e is JsonParseException || e is JSONException  -> {
                    Log.d(TAG, "数据解析异常" + e.message)
                    errorMsg = "数据解析异常"
                }
                else -> {
                    Log.d(TAG, "未知错误" + e.message)
                    errorMsg = "未知错误"
                }
            }
            return errorMsg
        }
    }
}