package com.example.mywanandroid.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * create by chenhanbin at 2019/2/23
 **/
 class IoMainScheduler<T>: BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())