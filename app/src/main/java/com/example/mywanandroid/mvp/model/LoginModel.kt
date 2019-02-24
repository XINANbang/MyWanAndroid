package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.LoginContract
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * created by chenhanbin on 2019/2/22 11:24
 *
 */
class LoginModel: BaseModel(), LoginContract.Model{
    override fun loginWanAndroid(username: String, password: String): Observable<HttpResult<LoginData>> {
        return HttpUtil.service.loginWanAndroid(username, password)
    }


}