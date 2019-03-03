package com.example.mywanandroid.api

import com.example.mywanandroid.mvp.model.bean.*
import com.squareup.moshi.Json
import io.reactivex.Observable
import retrofit2.http.*
import java.io.Serializable

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


    /**
     * 获取TODO列表数据
     * http://wanandroid.com/lg/todo/list/0/json
     * @param type
     */
    @POST("/lg/todo/list/{type}/json")
    fun getTodoList(@Path("type") type: Int): Observable<HttpResult<AllTodoResponseBody>>

    /**
     * 获取未完成Todo列表
     * http://wanandroid.com/lg/todo/listnotdo/0/json/1
     * @param type 类型拼接在链接上，目前支持0,1,2,3
     * @param page 拼接在链接上，从1开始
     */
    @POST("/lg/todo/listnotdo/{type}/json/{page}")
    fun getNoTodoList(@Path("page") page: Int, @Path("type") type: Int): Observable<HttpResult<TodoResponseBody>>

    /**
     * 获取已完成Todo列表
     * http://www.wanandroid.com/lg/todo/listdone/0/json/1
     * @param type 类型拼接在链接上，目前支持0,1,2,3
     * @param page 拼接在链接上，从1开始
     */
    @POST("/lg/todo/listdone/{type}/json/{page}")
    fun getDoneList(@Path("page") page: Int, @Path("type") type: Int): Observable<HttpResult<TodoResponseBody>>

    /**
     * V2版本 ： 获取TODO列表数据
     * http://www.wanandroid.com/lg/todo/v2/list/页码/json
     * @param page 页码从1开始，拼接在 url 上
     * @param map
     *          status 状态， 1-完成；0未完成; 默认全部展示；
     *          type 创建时传入的类型, 默认全部展示
     *          priority 创建时传入的优先级；默认全部展示
     *          orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     */
    @GET("/lg/todo/v2/list/{page}/json")
    fun getTodoList(@Path("page") page: Int, @QueryMap map: MutableMap<String, Any>): Observable<HttpResult<AllTodoResponseBody>>

    /**
     * 仅更新完成状态Todo
     * http://www.wanandroid.com/lg/todo/done/80/json
     * @param id 拼接在链接上，为唯一标识
     * @param status 0或1，传1代表未完成到已完成，反之则反之
     */
    @POST("/lg/todo/done/{id}/json")
    @FormUrlEncoded
    fun updateTodoById(@Path("id") id: Int, @Field("status") status: Int): Observable<HttpResult<Any>>

    /**
     * 删除一条Todo
     * http://www.wanandroid.com/lg/todo/delete/83/json
     * @param id
     */
    @POST("/lg/todo/delete/{id}/json")
    fun deleteTodoById(@Path("id") id: Int): Observable<HttpResult<Any>>

    /**
     * 新增一条Todo
     * http://www.wanandroid.com/lg/todo/add/json
     * @param body
     *          title: 新增标题
     *          content: 新增详情
     *          date: 2018-08-01
     *          type: 0
     */
    @POST("/lg/todo/add/json")
    @FormUrlEncoded
    fun addTodo(@FieldMap map: MutableMap<String, Any>): Observable<HttpResult<Any>>

    /**
     * 更新一条Todo内容
     * http://www.wanandroid.com/lg/todo/update/83/json
     * @param body
     *          title: 新增标题
     *          content: 新增详情
     *          date: 2018-08-01
     *          status: 0 // 0为未完成，1为完成
     *          type: 0
     */
    @POST("/lg/todo/update/{id}/json")
    @FormUrlEncoded
    fun updateTodo(@Path("id") id: Int, @FieldMap map: MutableMap<String, Any>): Observable<HttpResult<Any>>


}