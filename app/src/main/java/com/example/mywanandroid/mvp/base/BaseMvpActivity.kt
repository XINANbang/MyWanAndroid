package com.example.mywanandroid.mvp.base

/**
 * created by chenhanbin on 2019/2/22 17:47
 *
 */
abstract class BaseMvpActivity<in V: IView, P: IPresenter<V>>: BaseActivity(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        mPresenter = null
        super.onDestroy()
    }


}