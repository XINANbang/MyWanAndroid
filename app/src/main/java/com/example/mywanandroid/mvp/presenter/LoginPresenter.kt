package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.LoginContract
import com.example.mywanandroid.mvp.model.LoginModel
import com.example.mywanandroid.mvp.view.LoginActivity

/**
 * create by chenhanbin at 2019/2/23
 **/
class LoginPresenter: BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter {

    override fun createModel(): LoginContract.Model? = LoginModel()

    override fun loginWanAndroid(username: String, password: String) {
        mModel?.loginWanAndroid(username, password)?.ss(mModel, mView) {
            mView?.loginSuccess(it.data)
        }
    }

}