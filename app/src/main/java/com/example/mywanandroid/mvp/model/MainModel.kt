package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.MainContract
import com.example.mywanandroid.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/2/24
 **/
class MainModel: BaseModel(), MainContract.Mode {
    override fun logout(): Observable<HttpResult<Any>> {
        return HttpUtil.service.logout()
    }

}