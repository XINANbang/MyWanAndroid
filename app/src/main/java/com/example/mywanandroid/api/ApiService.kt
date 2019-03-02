package com.example.mywanandroid.api

import com.example.mywanandroid.mvp.model.bean.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * create by chenhanbin at 2019/2/23
 **/
interface ApiService{

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    fun loginWanAndroid(@Field("username") username: String,
                        @Field("password") password: String): Observable<HttpResult<LoginData>>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    fun registerWanAndroid(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("repassword") repassword: String): Observable<HttpResult<LoginData>>

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>


    /**
     * 获取Home页面顶部Banner轮播图
     */
    @GET("banner/json")
    fun requireHomeBanner(): Observable<HttpResult<List<Banner>>>


    /**
     * 获取Home页面文章列表
     */
    @GET("article/list/{pageNum}/json")
    fun requireHomeArticlesList(@Path("pageNum") pageNum: Int): Observable<HttpResult<ArticleList>>

    /**
     * 获取Home页面置顶文章
     */
    @GET("article/top/json")
    fun requireHomeTopArticles():Observable<HttpResult<MutableList<Article>>>

    /**
     * 收藏文章，标示为 id
     */
    @POST("lg/collect/{id}/json")
    fun addCollect(@Path("id")id: Int):Observable<HttpResult<Any>>

    /**
     * 获得收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") pageNum: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>

    /**
     * 文章列表取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollect(@Path("id")id: Int): Observable<HttpResult<Any>>

    /**
     * 收藏界面取消收藏
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(@Path("id") id: Int,
                             @Field("originId") originId: Int = -1): Observable<HttpResult<Any>>

}