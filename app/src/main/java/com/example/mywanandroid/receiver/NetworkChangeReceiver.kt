package com.example.mywanandroid.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.NetworkChangeEvent
import com.example.mywanandroid.utils.NetworkUtil
import com.example.mywanandroid.utils.Preference
import org.greenrobot.eventbus.EventBus

/**
 * created by chenhanbin on 2019/2/22 14:31
 *
 */
class NetworkChangeReceiver: BroadcastReceiver(){

    private var hasNetwork: Boolean by Preference(Constant.KEY_HAS_NETWORK, true)

    override fun onReceive(context: Context, intent: Intent?) {
        val isConnected = NetworkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }
    }

}