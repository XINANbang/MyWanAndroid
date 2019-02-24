package com.example.mywanandroid.mvp.model.bean

import com.squareup.moshi.Json

/**
 * created by chenhanbin on 2019/2/22 10:48
 *
 */

data class HttpResult<T>(
    @Json(name = "data") val data: T
) : BaseBean()

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