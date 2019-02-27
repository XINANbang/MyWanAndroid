package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/27
 **/
interface DetailsContract {
    interface Model: IMode {
        fun collect(id: Int): Observable<HttpResult<Any>>
        fun uncollect(id: Int): Observable<HttpResult<Any>>
    }

    interface View : IView{
        fun onCollect()
        fun onUncollect()
    }

    interface Presenter: IPresenter<View> {
        fun collect(id: Int)
        fun uncollect(id: Int)
    }
}