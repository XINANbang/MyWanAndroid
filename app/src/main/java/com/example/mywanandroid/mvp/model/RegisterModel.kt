package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.RegisterContract
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/24
 **/
class RegisterModel: BaseModel(), RegisterContract.Model {
    override fun registerWanAndroid(
        username: String,
        password: String,
        passwordRepeat: String
    ): Observable<HttpResult<LoginData>> {
        return HttpUtil.service.registerWanAndroid(username, password, passwordRepeat)
    }

}