package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.util.Log
import android.view.View
import com.example.mywanandroid.R
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.LoginEvent
import com.example.mywanandroid.mvp.base.BaseMvpActivity
import com.example.mywanandroid.mvp.contract.RegisterContract
import com.example.mywanandroid.mvp.model.bean.LoginData
import com.example.mywanandroid.mvp.presenter.RegisterPresenter
import com.example.mywanandroid.utils.Preference
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.longToast

/**
 * create by chenhanbin at 2019/2/24
 **/
class RegisterActivity: BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View{


    private var username: String by Preference(Constant.KEY_LOGIN_USERNAME, "")

    private var password: String by Preference(Constant.KEY_LOGIN_PASSWORD, "")

    private var token: String by Preference(Constant.KEY_TOKEN, "")


    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    override fun layoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        register_layout_btn_register.setOnClickListener(onClickListener)
        register_layout_btn_login.setOnClickListener(onClickListener)
    }

    override fun start() {

    }

    override fun showLoading() {
        //todo 显示加载弹窗
    }

    override fun hideLoading() {
        //todo 隐藏加载弹窗
    }

    override fun showToast(msg: String) {
        longToast(msg)
    }

    override fun showError() {
        longToast("")
    }

    override fun registerSuccess(data: LoginData) {
        showToast("注册成功")
        isLogin = true
        username = data.username
        password = data.password
        token = data.token

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun registerFail() {

    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.register_layout_btn_register -> {
                register()
            }
            R.id.register_layout_btn_login -> {
                var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    /**
     * Login
     */
    private fun register() {
        Log.d("chenhanbin", "register")
        if (validate()) {
            mPresenter?.registerWanAndroid(
                register_layout_et_username.text.toString(),
                register_layout_et_password.text.toString(),
                register_layout_et_password_repeat.text.toString())
        }
    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = register_layout_et_username.text.toString()
        val password: String = register_layout_et_password.text.toString()
        val passwordRepeat: String = register_layout_et_password_repeat.text.toString()

        if (username.isEmpty()) {
            register_layout_et_username.error = "不能为空"
            valid = false
        }
        if (password.isEmpty()) {
            register_layout_et_password.error =  "不能为空"
            valid = false
        }
        if (passwordRepeat.isEmpty()) {
            register_layout_et_password_repeat.error =  "不能为空"
            valid = false
        }
        return valid

    }

}