package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/24
 **/
interface MainContract {

    interface Mode: IMode {
        fun logout(): Observable<HttpResult<Any>>
    }

    interface View: IView {
        fun logoutSuccess(success: Boolean)
    }

    interface Presenter: IPresenter<View> {
        fun logout()
    }
}