package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.RegisterContract
import com.example.mywanandroid.mvp.model.RegisterModel

/**
 * create by chenhanbin at 2019/2/24
 **/
class RegisterPresenter: BasePresenter<RegisterContract.Model, RegisterContract.View>(), RegisterContract.Presenter{

    override fun createModel(): RegisterContract.Model? = RegisterModel()

    override fun registerWanAndroid(username: String, password: String, passwordRepeat: String) {
        mModel?.registerWanAndroid(username, password, passwordRepeat)?.ss(mModel, mView){
            mView?.registerSuccess(it.data)
        }
    }

}