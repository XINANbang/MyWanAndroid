package com.example.mywanandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.mywanandroid.utils.SettingUtil
import java.util.*
import kotlin.properties.Delegates

/**
 * created by chenhanbin on 2019/2/22 13:53
 *
 */
class App: Application() {

    companion object {
        private var TAG = "App"

        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initTheme()
    }

    /**
     * 初始化主题
     */
    private fun initTheme() {
        // 获取当前的主题
        if (SettingUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

}