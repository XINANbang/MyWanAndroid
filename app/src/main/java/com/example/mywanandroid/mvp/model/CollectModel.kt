package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.CollectContract
import com.example.mywanandroid.mvp.model.bean.CollectionArticle
import com.example.mywanandroid.mvp.model.bean.CollectionResponseBody
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/3/2
 **/
class CollectModel: BaseModel(), CollectContract.Model{
    override fun getCollectDatas(pageNum: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>> {
        return HttpUtil.service.getCollectList(pageNum)
    }

    override fun addCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.addCollect(id)
    }

    override fun removeCollect(id: Int, originId: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.removeCollectArticle(id, originId)
    }

}