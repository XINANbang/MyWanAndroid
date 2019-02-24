package com.example.mywanandroid.http.intercepter

import com.example.mywanandroid.constant.HttpConstant
import com.example.mywanandroid.utils.Preference
import okhttp3.Interceptor
import okhttp3.Response

/**
 * create by chenhanbin at 2019/2/23
 **/
class HeadInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")

        val domain = request.url().host()
        val url = request.url().toString()
        if ( domain.isNotEmpty() && (url.contains(HttpConstant.COLLECTIONS_WEBSITE))
                    || (url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE))
                    || (url.contains(HttpConstant.ARTICLE_WEBSITE))
                    || (url.contains(HttpConstant.TODO_WEBSITE)) ) {
            val spDomain: String by Preference(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
            }

        }
        return chain.proceed(builder.build())
    }

}