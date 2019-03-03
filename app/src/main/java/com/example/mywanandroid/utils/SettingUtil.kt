package com.example.mywanandroid.utils

import android.graphics.Color
import android.preference.PreferenceManager
import com.example.mywanandroid.App
import com.example.mywanandroid.R

/**
 * create by chenhanbin at 2019/3/3
 **/
object SettingUtil{
    private val setting = PreferenceManager.getDefaultSharedPreferences(App.context)

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = App.context.resources.getColor(R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) defaultColor else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        setting.edit().putInt("color", color).apply()
    }

    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        setting.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return setting.getBoolean("switch_nightMode", false)
    }

    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        return setting.getBoolean("switch_noPhotoMode", false) //&& NetWorkUtil.isMobile(App.context)
    }
}