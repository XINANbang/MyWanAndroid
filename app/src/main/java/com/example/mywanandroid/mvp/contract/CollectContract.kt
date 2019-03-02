package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.CollectionArticle
import com.example.mywanandroid.mvp.model.bean.CollectionResponseBody
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/3/2
 **/
interface CollectContract {

    interface Model: IMode {
        fun getCollectDatas(pageNum: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>
        fun addCollect(id: Int): Observable<HttpResult<Any>>
        fun removeCollect(id: Int, originId: Int): Observable<HttpResult<Any>>
    }

    interface View: IView {
        fun setDatas(datas: CollectionResponseBody<CollectionArticle>)
        fun onCollected()
        fun onUncollect()
    }

    interface Presenter: IPresenter<View> {
        fun getCollectDatas(pageNum: Int)
        fun addCollect(id: Int)
        fun removeCollect(id: Int, originId: Int)
    }
}