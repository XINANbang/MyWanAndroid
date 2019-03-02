package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.CollectContract
import com.example.mywanandroid.mvp.model.CollectModel

/**
 * create by chenhanbin at 2019/3/2
 **/
class CollectPresenter: BasePresenter<CollectContract.Model, CollectContract.View>(), CollectContract.Presenter{
    override fun removeCollect(id: Int, originId: Int) {
        mModel?.removeCollect(id, originId)?.ss(mModel, mView){
            mView?.onUncollect()
        }
    }

    override fun createModel(): CollectContract.Model? = CollectModel()

    override fun getCollectDatas(pageNum: Int) {
        mModel?.getCollectDatas(pageNum)?.ss(mModel, mView){
            mView?.setDatas(it.data)
        }
    }

    override fun addCollect(id: Int) {
        mModel?.addCollect(id)?.ss(mModel, mView){
            mView?.onCollected()
        }
    }


}