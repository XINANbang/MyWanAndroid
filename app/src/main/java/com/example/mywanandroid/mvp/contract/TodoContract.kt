package com.example.mywanandroid.mvp.contract

import com.example.mywanandroid.mvp.base.IMode
import com.example.mywanandroid.mvp.base.IPresenter
import com.example.mywanandroid.mvp.base.IView
import com.example.mywanandroid.mvp.model.bean.AllTodoResponseBody
import com.example.mywanandroid.mvp.model.bean.HttpResult
import com.example.mywanandroid.mvp.model.bean.TodoResponseBody
import io.reactivex.Observable

/**
 * create by chenhanbin at 2019/3/3
 **/
interface TodoContract {
    interface Model: IMode {
        fun getTodoList(type: Int): Observable<HttpResult<AllTodoResponseBody>>

        fun getNoTodoList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>>

        fun getDoneList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>>

        fun deleteTodoById(id: Int): Observable<HttpResult<Any>>

        fun updateTodoById(id: Int, status: Int): Observable<HttpResult<Any>>
    }

    interface View:IView {
        fun showNoTodoList(todoResponseBody: TodoResponseBody)

        fun showDeleteSuccess(success: Boolean)

        fun showUpdateSuccess(success: Boolean)
    }

    interface Presenter: IPresenter<View> {
        fun getAllTodoList(type: Int)

        fun getNoTodoList(page: Int, type: Int)

        fun getDoneList(page: Int, type: Int)

        fun deleteTodoById(id: Int)

        fun updateTodoById(id: Int, status: Int)
    }
}