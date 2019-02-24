package com.example.mywanandroid.mvp.base

/**
 * created by chenhanbin on 2019/2/22 10:57
 *
 */
interface IPresenter<in V: IView> {

    fun attachView(mView: V)

    fun detachView()
}