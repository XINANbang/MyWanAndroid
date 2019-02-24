package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mywanandroid.App
import com.example.mywanandroid.R
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.LoginEvent
import com.example.mywanandroid.mvp.base.BaseMvpActivity
import com.example.mywanandroid.mvp.contract.LoginContract
import com.example.mywanandroid.mvp.model.bean.LoginData
import com.example.mywanandroid.mvp.presenter.LoginPresenter
import com.example.mywanandroid.utils.Preference
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.longToast
import java.lang.invoke.ConstantCallSite

/**
 * create by chenhanbin at 2019/2/23
 **/
class LoginActivity: BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    private var username: String by Preference(Constant.KEY_LOGIN_USERNAME, "")

    private var password: String by Preference(Constant.KEY_LOGIN_PASSWORD, "")

    private var token: String by Preference(Constant.KEY_TOKEN, "")


    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        Log.d("chenhanbin", "login = " + isLogin)
        Log.d("chenhanbin", "login = " + username)
        Log.d("chenhanbin", "login = " + password)
        Log.d("chenhanbin", "login = " + token)
    }

    override fun initView() {
        super.initView()
        login_layout_et_username.setText("oooaoaoa")
        login_layout_btn_login.setOnClickListener(onClickListener)
        login_layout_btn_register.setOnClickListener(onClickListener)
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

    override fun loginSuccess(data: LoginData) {
        showToast("登陆成功")
        Log.d("chenhanbin0", "登陆成功")
        isLogin = true
        username = data.username
        password = data.password
        token = data.token
        Log.d("chenhanbin0", "token = " + token + " , user = " + username + " , password = " + password)
        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun loginFail() {

    }

    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.login_layout_btn_login -> {
                login()
            }
            R.id.login_layout_btn_register -> {
                var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    /**
     * Login
     */
    private fun login() {
        Log.d("chenhanbin", "login")
        if (validate()) {
            mPresenter?.loginWanAndroid(login_layout_et_username.text.toString(), login_layout_et_password.text.toString())
        }
    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = login_layout_et_username.text.toString()
        val password: String = login_layout_et_password.text.toString()

        if (username.isEmpty()) {
            login_layout_et_username.error = "不能为空"
            valid = false
        }
        if (password.isEmpty()) {
            login_layout_et_password.error =  "不能为空"
            valid = false
        }
        return valid

    }

}
