package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

interface LoginContract {

    interface Model: IMode {
        fun loginWanAndroid(username: String, password: String) : Observable<HttpResult<LoginData>>
    }

    interface Presenter: IPresenter<View>{
        fun loginWanAndroid(username: String, password: String)
    }

    interface View: IView{
        fun loginSuccess(data: LoginData)
        fun loginFail()
    }
}