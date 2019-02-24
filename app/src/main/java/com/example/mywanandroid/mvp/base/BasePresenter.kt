package com.example.mywanandroid.mvp.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * created by chenhanbin on 2019/2/22 17:21
 *
 */
abstract class BasePresenter<M: IMode, V: IView>: IPresenter<V>, LifecycleObserver {

    protected var mModel: M? = null
    protected var mView: V? = null

    private val isViewAttached: Boolean
        get() = mView != null

    private var mCompositeDisposable: CompositeDisposable? = null

    /**
     * 创建Model
     */
    open fun createModel(): M? = null

    /**
     * 是否使用EventBus
     */
    open fun useEventBus(): Boolean = false

    open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    override fun attachView(mView: V) {
        this.mView = mView
        mModel = createModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
            }
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun detachView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
        mModel?.onDetach()
        mModel = null
        mView = null

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}