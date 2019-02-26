package com.example.mywanandroid.ext

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.mywanandroid.R

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