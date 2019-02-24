package com.example.mywanandroid.ext

import com.cxz.wanandroid.http.function.RetryWithDelay
import com.example.mywanandroid.App
import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.BaseBean
import com.example.mywanandroid.rx.Exception.ErrorStatus
import com.example.mywanandroid.rx.Exception.ExceptionPares
import com.example.mywanandroid.rx.SchedulerUtil
import com.example.mywanandroid.utils.NetworkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * create by chenhanbin at 2019/2/23
 **/

fun <T: BaseBean> Observable<T>.ss(
    model: IMode?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.compose(SchedulerUtil.ioMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) {
                    view?.showLoading()
                }
                model?.addDisposable(d)
                if (!NetworkUtil.isNetworkConnected(App.context)) {
                    view?.showToast("网络错误")
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when {
                    t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                    else -> view?.showToast("出错")
                }
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                view?.showToast(ExceptionPares.pares(e))
            }
        })
}