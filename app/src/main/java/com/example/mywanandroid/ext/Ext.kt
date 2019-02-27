package com.example.mywanandroid.ext

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mywanandroid.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient

/**
 * created by chenhanbin on 2019/2/26 18:28
 *
 */
fun ImageView.load(context: Context?, url: String?) {
    Glide.with(context!!).clear(this)

    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.bg_placeholder)

    Glide.with(context!!)
        .load(url)
        .transition(DrawableTransitionOptions().crossFade())
        .apply(options)
        .into(this)
}

fun String.getWebView(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webChromeClient: WebChromeClient,
    webViewClient: WebViewClient
): AgentWeb = AgentWeb.with(activity)
    .setAgentWebParent(webContent, 1, layoutParams)
    .useDefaultIndicator()
    .setWebView(webView)
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
    .createAgentWeb()
    .ready()
    .go(this)
