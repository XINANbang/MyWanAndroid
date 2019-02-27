package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.HomeContract
import com.example.mywanandroid.mvp.model.bean.*
import io.reactivex.Observable

/**
 * created by chenhanbin on 2019/2/25 15:51
 *
 */
class HomeModel: BaseModel(), HomeContract.Model{
    override fun requireBanner(): Observable<HttpResult<List<Banner>>> {
        return HttpUtil.service.requireHomeBanner()
    }

    override fun requireTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return HttpUtil.service.requireHomeTopArticles()
    }

    override fun requireArticlesList(pageNum: Int): Observable<HttpResult<ArticleList>> {
        return HttpUtil.service.requireHomeArticlesList(pageNum)
    }

    override fun addCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.addCollect(id)
    }

    override fun cancelCollect(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.cancelCollect(id)
    }

}