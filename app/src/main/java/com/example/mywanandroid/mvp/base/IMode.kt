package com.example.mywanandroid.mvp.base

import io.reactivex.disposables.Disposable

/**
 * created by chenhanbin on 2019/2/22 10:56
 *
 */
interface IMode {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()
}