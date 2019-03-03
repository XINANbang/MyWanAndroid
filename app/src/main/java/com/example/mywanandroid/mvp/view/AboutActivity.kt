package com.example.mywanandroid.mvp.view

import com.example.mywanandroid.BuildConfig
import com.example.mywanandroid.R
import com.example.mywanandroid.mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * create by chenhanbin at 2019/3/3
 **/
class AboutActivity: BaseActivity() {
    override fun layoutId(): Int = R.layout.activity_about

    override fun initData() {
    }

    override fun initView() {
        activity_about_version_info.run {
            text = "V" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"
        }
    }

    override fun start() {
    }

}