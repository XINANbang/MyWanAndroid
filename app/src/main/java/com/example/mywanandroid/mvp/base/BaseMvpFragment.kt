package com.example.mywanandroid.mvp.base

import android.view.View

/**
 * create by chenhanbin at 2019/2/23
 **/
abstract class BaseMvpFragment<in V: IView, P: IPresenter<V>>: BaseFragment(), IView {
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroy()
    }
}