package com.example.mywanandroid.mvp.model

import com.example.mywanandroid.http.HttpUtil
import com.example.mywanandroid.mvp.base.BaseModel
import com.example.mywanandroid.mvp.contract.TodoContract
import com.example.mywanandroid.mvp.model.bean.AllTodoResponseBody
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.TodoResponseBody
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/3/3
 **/
class TodoModel: BaseModel(), TodoContract.Model{
    override fun getTodoList(type: Int): Observable<HttpResult<AllTodoResponseBody>> {
        return HttpUtil.service.getTodoList(type)
    }

    override fun getNoTodoList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>> {
        return HttpUtil.service.getNoTodoList(page, type)
    }

    override fun getDoneList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>> {
        return HttpUtil.service.getDoneList(page, type)
    }

    override fun deleteTodoById(id: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.deleteTodoById(id)
    }

    override fun updateTodoById(id: Int, status: Int): Observable<HttpResult<Any>> {
        return HttpUtil.service.updateTodoById(id, status)
    }
}