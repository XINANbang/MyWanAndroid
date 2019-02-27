package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.Article
import com.example.mywanandroid.mvp.model.bean.ArticleList
import com.example.mywanandroid.mvp.model.bean.Banner
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * created by chenhanbin on 2019/2/25 15:51
 *
 */
interface HomeContract {

    interface Model: IMode{

        fun requireBanner(): Observable<HttpResult<List<Banner>>>

        fun requireTopArticles(): Observable<HttpResult<MutableList<Article>>>

        fun requireArticlesList(pageNum: Int): Observable<HttpResult<ArticleList>>

        fun addCollect(id: Int): Observable<HttpResult<Any>>

        fun cancelCollect(id: Int): Observable<HttpResult<Any>>
    }

    interface View: IView{

        fun scrollToTop()

        fun setBanner(banners: List<Banner>)

        fun setArticlesList(articleList: ArticleList)

    }

    interface Presenter: IPresenter<View>{

        fun requestHomeData()

        fun requireBanner()

        fun requireArticlesList(pageNum: Int)

        fun addCollect(id: Int)

        fun cancelCollect(id: Int)
    }
}