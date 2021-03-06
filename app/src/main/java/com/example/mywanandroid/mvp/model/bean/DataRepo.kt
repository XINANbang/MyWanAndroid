package com.example.mywanandroid.mvp.model.bean

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * created by chenhanbin on 2019/2/22 10:48
 *
 */

data class HttpResult<T>(
    @Json(name = "data") val data: T
) : BaseBean()

//登录信息
data class LoginData(
    @Json(name= "chapterTops") val chapterTops: MutableList<String>,
    @Json(name = "collectIds") val collectIds: MutableList<String>,
    @Json(name = "email") val email: String,
    @Json(name = "icon") val icon: String,
    @Json(name = "id") val id: Int,
    @Json(name = "password") val password: String,
    @Json(name = "token") val token: String,
    @Json(name = "type") val type: Int,
    @Json(name = "username") val username: String
)

//文章列表
data class ArticleList(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") var datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

//文章
data class Article(
    @Json(name = "apkLink") val apkLink: String,
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "collect") var collect: Boolean,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "fresh") val fresh: Boolean,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "projectLink") val projectLink: String,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "superChapterId") val superChapterId: Int,
    @Json(name = "superChapterName") val superChapterName: String,
    @Json(name = "tags") val tags: MutableList<Tag>,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int,
    @Json(name = "top") var isTop: String
)

//文章标签
data class Tag(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

//home界面，banner轮播图
data class Banner(
    @Json(name = "desc") val desc: String,
    @Json(name = "id") val id: Int,
    @Json(name = "imagePath") val imagePath: String,
    @Json(name = "isVisible") val isVisible: Int,
    @Json(name = "order") val order: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "url") val url: String
)

//返回的收藏结果
data class CollectionResponseBody<T>(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: List<T>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

//收藏的文章
data class CollectionArticle(
    @Json(name = "author") val author: String,
    @Json(name = "chapterId") val chapterId: Int,
    @Json(name = "chapterName") val chapterName: String,
    @Json(name = "courseId") val courseId: Int,
    @Json(name = "desc") val desc: String,
    @Json(name = "envelopePic") val envelopePic: String,
    @Json(name = "id") val id: Int,
    @Json(name = "link") val link: String,
    @Json(name = "niceDate") val niceDate: String,
    @Json(name = "origin") val origin: String,
    @Json(name = "originId") val originId: Int,
    @Json(name = "publishTime") val publishTime: Long,
    @Json(name = "title") val title: String,
    @Json(name = "userId") val userId: Int,
    @Json(name = "visible") val visible: Int,
    @Json(name = "zan") val zan: Int
)

// TODO工具 类型
data class TodoTypeBean(
    val type: Int,
    val name: String,
    var isSelected: Boolean
)

// TODO实体类
data class TodoBean(
    @Json(name = "id") val id: Int,
    @Json(name = "completeDate") val completeDate: String,
    @Json(name = "completeDateStr") val completeDateStr: String,
    @Json(name = "content") val content: String,
    @Json(name = "date") val date: Long,
    @Json(name = "dateStr") val dateStr: String,
    @Json(name = "status") val status: Int,
    @Json(name = "title") val title: String,
    @Json(name = "type") val type: Int,
    @Json(name = "userId") val userId: Int,
    @Json(name = "priority") val priority: Int
) : Serializable

data class TodoListBean(
    @Json(name = "date") val date: Long,
    @Json(name = "todoList") val todoList: MutableList<TodoBean>
)

// 所有TODO，包括待办和已完成
data class AllTodoResponseBody(
    @Json(name = "type") val type: Int,
    @Json(name = "doneList") val doneList: MutableList<TodoListBean>,
    @Json(name = "todoList") val todoList: MutableList<TodoListBean>
)

data class TodoResponseBody(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: MutableList<TodoBean>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

// 新增TODO的实体
data class AddTodoBean(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "date") val date: String,
    @Json(name = "type") val type: Int
)

// 更新TODO的实体
data class UpdateTodoBean(
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "date") val date: String,
    @Json(name = "status") val status: Int,
    @Json(name = "type") val type: Int
)