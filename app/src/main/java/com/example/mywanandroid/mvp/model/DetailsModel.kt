package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.DetailsContract
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/27
 **/
class DetailsModel: BaseModel(), DetailsContract.Model {
    override fun collect(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.addCollect(id)
    }

    override fun uncollect(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.cancelCollect(id)
    }
}