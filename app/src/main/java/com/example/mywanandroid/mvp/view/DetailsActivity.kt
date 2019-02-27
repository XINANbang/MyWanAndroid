package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.mywanandroid.R
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.ext.getWebView
import com.example.mywanandroid.mvp.base.BaseMvpActivity
import com.example.mywanandroid.mvp.contract.DetailsContract
import com.example.mywanandroid.mvp.presenter.DetailsPresenter
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.longToast

/**
 * create by chenhanbin at 2019/2/27
 **/
class DetailsActivity: BaseMvpActivity<DetailsContract.View, DetailsContract.Presenter>(), DetailsContract.View {

    private var agentWeb: AgentWeb? = null
    private lateinit var intentTitle: String
    private lateinit var intentUrl: String
    private var intentId: Int = 0
    private val webView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override fun createPresenter(): DetailsContract.Presenter = DetailsPresenter()

    override fun layoutId(): Int = R.layout.activity_details

    override fun initData() {
    }

    override fun initView() {
        super.initView()
        toolbar.apply {
            title = ""
            setSupportActionBar(this)
            //返回键
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        detail_tv_title.apply {
            text = "加载中……"
            visibility = View.VISIBLE
            postDelayed({detail_tv_title.isSelected = true}, 2000)
        }
        intent.extras?.let {
            intentId = it.getInt(Constant.KEY_DETAIL_ID, -1)
            intentTitle = it.getString(Constant.KEY_DETAIL_TITLE, "")
            intentUrl = it.getString(Constant.KEY_DETAIL_URL_KEY, "")
        }

        initWebView()
    }

    fun initWebView() {
        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        agentWeb = intentUrl.getWebView(this,
            details_container,
            layoutParams,
            webView,
            webChromeClient,
            webViewClient)

        agentWeb?.webCreator?.webView?.let {
            it.settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            title.let {
                // toolbar.title = it
                detail_tv_title.text = it
            }
        }
    }

    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            // super.onReceivedSslError(view, handler, error)
            handler?.proceed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.share_format, getString(R.string.app_name), intentTitle, intentUrl))
                    type = Constant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, "分享到："))
                }
                return true
            }
            R.id.action_collect -> {
                if (isLogin) {
                    mPresenter?.collect(intentId)
                } else {
                    Intent(this, LoginActivity::class.java).run {
                        startActivity(this)
                    }
                }
                return true
            }
            R.id.action_browser -> {
                Intent().run {
                    action = "android.intent.action.VIEW"
                    data = Uri.parse(intentUrl)
                    startActivity(this)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        agentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        super.onResume()
        agentWeb?.webLifeCycle?.onResume()
    }

    override fun onPause() {
        super.onPause()
        agentWeb?.webLifeCycle?.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
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

    override fun onCollect() {

    }

    override fun onUncollect() {

    }
}