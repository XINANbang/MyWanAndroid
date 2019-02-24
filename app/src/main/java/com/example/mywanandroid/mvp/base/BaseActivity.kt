package com.example.mywanandroid.mvp.base

import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.NetworkChangeEvent
import com.example.mywanandroid.receiver.NetworkChangeReceiver
import com.example.mywanandroid.utils.KeyBoardUtil
import com.example.mywanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * created by chenhanbin on 2019/2/22 13:50
 *
 */
abstract class BaseActivity: AppCompatActivity() {
    /**
     * 是否登陆
     */
    protected var isLogin: Boolean by Preference(Constant.KEY_LOGIN_STATE, false)

    /**
     * 上一次网络状态的缓存
     */
    protected var hasNetwork: Boolean by Preference(Constant.KEY_HAS_NETWORK, true)

    /**
     * 网络变化监听广播
     */
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    /**
     * 主题颜色
     */
    protected var mThemeColor: Int = 0

    /**
     * 布局文件id
     */
    protected abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initData()
        initView()
        start()
    }

    override fun onResume() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()

    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun initToolbar(toolbar: Toolbar, homeAsUpEnable: Boolean, title: String){
        toolbar?.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnable)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        if (event.isConnected) {
            doReConnected()
        }
    }
}