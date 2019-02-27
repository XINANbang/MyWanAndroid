package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.DetailsContract
import com.example.mywanandroid.mvp.model.DetailsModel

/**
 * create by chenhanbin at 2019/2/27
 **/
class DetailsPresenter: BasePresenter<DetailsContract.Model, DetailsContract.View>(), DetailsContract.Presenter {

    override fun createModel(): DetailsContract.Model? = DetailsModel()

    override fun collect(id: Int) {
        mModel?.collect(id)?.ss(mModel, mView){
            mView?.onCollect()
        }
    }

    override fun uncollect(id: Int) {
        mModel?.uncollect(id)?.ss(mModel, mView){
            mView?.onUncollect()
        }
    }

}