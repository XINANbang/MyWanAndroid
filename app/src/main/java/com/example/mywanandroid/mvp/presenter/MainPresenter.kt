package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.MainContract
import com.example.mywanandroid.mvp.model.MainModel

/**
 * create by chenhanbin at 2019/2/24
 **/
class MainPresenter: BasePresenter<MainContract.Mode, MainContract.View>(), MainContract.Presenter {
    override fun createModel(): MainContract.Mode? = MainModel()

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView){
            mView?.logoutSuccess(true)
        }
    }

}