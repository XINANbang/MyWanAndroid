package com.example.mywanandroid.mvp.presenter

import com.example.mywanandroid.ext.ss
import com.example.mywanandroid.mvp.base.BasePresenter
import com.example.mywanandroid.mvp.contract.HomeContract
import com.example.mywanandroid.mvp.model.HomeModel
import com.example.mywanandroid.mvp.model.bean.Article
import com.example.mywanandroid.mvp.model.bean.ArticleList
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * created by chenhanbin on 2019/2/25 15:50
 *
 */
class HomePresenter: BasePresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter{

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun requestHomeData() {
        requireBanner()
        val observable = Observable.zip(mModel?.requireTopArticles(), mModel?.requireArticlesList(0),
            BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleList>, HttpResult<ArticleList>>{
                t1, t2 ->
                t1.data.forEach { it.isTop = "1" }
                t2.data.datas.addAll(0, t1.data)
                t2
            })
        observable?.ss(mModel, mView){
            mView?.setArticlesList(it.data)
        }
    }

    override fun requireBanner() {
        mModel?.requireBanner()?.ss(mModel, mView){
            mView?.setBanner(it.data)
        }
    }

    override fun requireArticlesList(pageNum: Int) {
        mModel?.requireArticlesList(pageNum)?.ss(mModel, mView){
            mView?.setArticlesList(it.data)
        }
    }

    override fun addCollect(id: Int) {
        mModel?.addCollect(id)?.ss(mModel, mView) {

        }
    }

    override fun cancelCollect(id: Int) {
        mModel?.cancelCollect(id)?.ss(mModel, mView){

        }
    }
}