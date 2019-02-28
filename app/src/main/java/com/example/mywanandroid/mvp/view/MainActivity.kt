package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mywanandroid.R
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.LoginEvent
import com.example.mywanandroid.mvp.base.BaseMvpActivity
import com.example.mywanandroid.mvp.contract.MainContract
import com.example.mywanandroid.mvp.presenter.MainPresenter
import com.example.mywanandroid.utils.Preference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private val username: String by Preference(Constant.KEY_LOGIN_USERNAME, "")

    private var nav_username: TextView? = null

    private var homeFragment: HomeFragment? = null

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun layoutId(): Int = R.layout.activity_main

    override fun initData() {
    }

    override fun initView() {
        toolbar.run {
            title = "玩Android"
            setSupportActionBar(toolbar)
        }

        main_layout_fab.setOnClickListener { view -> kotlin.run{
               homeFragment?.scrollToTop()
            }
        }

        initDrawLayout()

        main_layout_bottom_nav.run {
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        main_layout_drawerlayout_nav.run {
            setNavigationItemSelectedListener(navigationItemSelectedListener)
            menu.findItem(R.id.nav_logout).isVisible = isLogin
            nav_username = getHeaderView(0).findViewById(R.id.main_layout_head_tv_username)
        }
        nav_username?.run {
            text = if (!isLogin) {
                "登录00"
            } else {
                username
            }
            setOnClickListener {
                Log.d("chenhanbin", "isLogin = " + isLogin)
                if (!isLogin) {
                    Intent(this@MainActivity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
        }
        showFragment(FRAGMENT_TYPE.HOME)
        super.initView()
    }

    private fun initDrawLayout() {
        main_layout_drawerlayout.run {
            var toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun start() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showToast(msg: String) {
        longToast(msg)
    }

    override fun showError() {
    }

    override fun logoutSuccess(success: Boolean) {
        if (success) {
            doAsync {
                // CookieManager().clearAllCookies()
                Preference.clearPreference()
                uiThread {
                    showToast("退出登录成功")
                    isLogin = false
                    EventBus.getDefault().post(LoginEvent(false))
                    Intent(this@MainActivity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
        }
    }

    enum class FRAGMENT_TYPE {
        HOME, KNOWLEDGE, NAVIGATION, PROJECT, WECHATE
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item -> return@OnNavigationItemSelectedListener when (item.itemId) {
            R.id.action_home -> {
                showFragment(FRAGMENT_TYPE.HOME)
                true
            }
            R.id.action_todo -> {
                showFragment(FRAGMENT_TYPE.KNOWLEDGE)
                true
            }
//            R.id.action_navigation -> {
//                showFragment(FRAGMENT_TYPE.NAVIGATION)
//                true
//            }
//            R.id.action_project -> {
//                showFragment(FRAGMENT_TYPE.PROJECT)
//                true
//            }
//            R.id.action_wechat -> {
//                showFragment(FRAGMENT_TYPE.WECHATE)
//                true
//            }
            else -> false
        }
    }

    private fun showFragment(type: FRAGMENT_TYPE) {
        Log.d("chenhanbin", "showFragment: " +type)
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        if (homeFragment == null) {
            homeFragment = HomeFragment.getInstance()
            transaction.add(R.id.main_layout_container, homeFragment!!, "home")
        } else {
            transaction.show(homeFragment!!)
        }
        transaction.commit()
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        homeFragment?.let { transaction.hide(it) }
    }

    private val navigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener{
        item -> when(item.itemId) {
            R.id.nav_collect -> {
                if (isLogin) {
                    showToast("点击了收藏")
                } else {
                    showToast("请先登录")
                    Intent(this@MainActivity, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
            }
            R.id.nav_about -> {


            }
            R.id.nav_night_mode -> {

            }
            R.id.nav_todo -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_logout -> {
                mPresenter?.logout()
            }
        }
        true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(event: LoginEvent) {
        if (event.isLogin) {
            nav_username?.text = username
            main_layout_drawerlayout_nav.menu.findItem(R.id.nav_logout).isVisible = true
        } else {
            nav_username?.text = "请登录"
            main_layout_drawerlayout_nav.menu.findItem(R.id.nav_logout).isVisible = false
        }
    }
}
