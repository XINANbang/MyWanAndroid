package com.example.mywanandroid.mvp.base

/**
 * created by chenhanbin on 2019/2/22 10:57
 *
 */
interface IView {

    fun showLoading()

    fun hideLoading()

    fun showToast(msg: String)

    fun showError()
}