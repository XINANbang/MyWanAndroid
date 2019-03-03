package com.example.mywanandroid.mvp.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.NetworkChangeEvent
import com.example.mywanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * created by chenhanbin on 2019/2/22 16:24
 *
 */
abstract class BaseFragment: Fragment() {

    /**
     * 是否登陆
     */
    protected var isLogin: Boolean by Preference(Constant.KEY_LOGIN_STATE, false)

    /**
     * 上一次网络状态的缓存
     */
    protected var hasNetwork: Boolean by Preference(Constant.KEY_HAS_NETWORK, true)

    /**
     * 视图是否加载过
     */
    private var isViewPrepare = false

    /**
     * 数据是否加载过
     */
    private var isDataLoaded = false

    /**
     * 布局
     */
    @LayoutRes
    abstract fun layoutId(): Int

    /**
     * 初始化View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否使用EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("chenhanbin", "setUserVisibleHint = " + isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepare()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepare()
    }

    override fun onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    private fun lazyLoadDataIfPrepare() {
        if (userVisibleHint && isViewPrepare && !isDataLoaded) {
            lazyLoad()
            isDataLoaded = true
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            doReConnected()
        }
    }
}