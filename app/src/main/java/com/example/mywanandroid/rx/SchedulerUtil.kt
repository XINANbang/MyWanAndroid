package com.example.mywanandroid.rx

import com.example.mywanandroid.rx.scheduler.IoMainScheduler

/**
 * create by chenhanbin at 2019/2/23
 **/
object SchedulerUtil {
    fun <T> ioMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}