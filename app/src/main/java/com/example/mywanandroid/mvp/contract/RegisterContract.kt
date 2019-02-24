package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/24
 **/
interface RegisterContract {

    interface Model: IMode{
        fun registerWanAndroid(username: String, password: String, passwordRepeat: String): Observable<HttpResult<LoginData>>
    }

    interface View: IView {
        fun registerSuccess(data: LoginData)
        fun registerFail()
    }

    interface Presenter: IPresenter<View> {
        fun registerWanAndroid(username: String, password: String, passwordRepeat: String)
    }
}