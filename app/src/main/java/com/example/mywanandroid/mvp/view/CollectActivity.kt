package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mywanandroid.App
import com.example.mywanandroid.R
import com.example.mywanandroid.adapter.CollectAdapter
import com.example.mywanandroid.adapter.HomeAdapter
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.CollectEvent
import com.example.mywanandroid.event.LoginEvent
import com.example.mywanandroid.ext.load
import com.example.mywanandroid.mvp.base.BaseMvpActivity
import com.example.mywanandroid.mvp.contract.CollectContract
import com.example.mywanandroid.mvp.model.bean.Article
import com.example.mywanandroid.mvp.model.bean.Banner
import com.example.mywanandroid.mvp.model.bean.CollectionArticle
import com.example.mywanandroid.mvp.model.bean.CollectionResponseBody
import com.example.mywanandroid.mvp.presenter.CollectPresenter
import com.example.mywanandroid.utils.NetworkUtil
import com.example.mywanandroid.utils.SettingUtil
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.longToast

/**
 * create by chenhanbin at 2019/3/2
 **/
class CollectActivity: BaseMvpActivity<CollectContract.View, CollectContract.Presenter>(), CollectContract.View{

    /**
     * 文章数据
     */
    private val articlesDatas = mutableListOf<CollectionArticle>()


    /**
     * 文章适配器
     */
    private val collectAdapter: CollectAdapter by lazy{
        CollectAdapter(this, articlesDatas)
    }

    /**
     * 是否刷新，区别刷新和加载更多
     */
    private var isRefresh = true


    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun createPresenter(): CollectContract.Presenter = CollectPresenter()

    override fun layoutId(): Int = R.layout.activity_collect

    override fun initData() {

    }

    override fun initColor() {
        super.initColor()
        activity_collect_swip_refresh_layout.setBackgroundColor(resources.getColor(if (SettingUtil.getIsNightMode()) R.color.Grey800 else R.color.White))
    }

    override fun initView() {
        super.initView()
        activity_collect_toolbar.apply {
            title = "收藏"
            setSupportActionBar(this)
            //返回键
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        activity_collect_swip_refresh_layout.setOnRefreshListener {
            collectAdapter.setEnableLoadMore(false)
            mPresenter?.getCollectDatas(0)
            isRefresh = true
        }
        activity_collect_recycleview.run {
            layoutManager = linearLayoutManager
            adapter = collectAdapter
            itemAnimator = DefaultItemAnimator()
        }
        collectAdapter.run {
            bindToRecyclerView(activity_collect_recycleview)
            setOnLoadMoreListener(onLoadMoreListener, activity_collect_recycleview)
            onItemClickListener = this@CollectActivity.onItemClickListener
            onItemChildClickListener = this@CollectActivity.onItemChildClickListener
        }
        mPresenter?.getCollectDatas(0)
    }

    override fun start() {
        mPresenter?.getCollectDatas(0)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        activity_collect_swip_refresh_layout.isRefreshing = false
        if (isRefresh) {
            collectAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showToast(msg: String) {
        longToast(msg)
    }

    override fun showError() {
    }

    override fun setDatas(datas: CollectionResponseBody<CollectionArticle>) {
        datas.datas.let {
            collectAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                Log.d("chenhanbin", "size = " + size + ", datas.size = " + datas.size + " , isRefresh = " + isRefresh)
                if (size < datas.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
    }

    override fun onCollected() {

    }

    override fun onUncollect() {

    }

    private val onLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener{
        val pageNum = collectAdapter.data.size / 20
        mPresenter?.getCollectDatas(pageNum)
        isRefresh = false
    }

    private val onItemClickListener =  BaseQuickAdapter.OnItemClickListener{ adapter, view, position ->
        if (articlesDatas.size != 0) {
            val data = articlesDatas[position]
//            展示详情
            Intent(this, DetailsActivity::class.java).run {
                putExtra(Constant.KEY_DETAIL_ID, data.id)
                putExtra(Constant.KEY_DETAIL_ORIGIN_ID, data.originId)
                putExtra(Constant.KEY_DETAIL_URL_KEY, data.link)
                putExtra(Constant.KEY_DETAIL_TITLE, data.title)
                putExtra(Constant.KEY_DETAIL_COLLECT, true)
                startActivity(this)
            }
        }

    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener{ adapter, view, position ->
        if (articlesDatas.size > 0) {
            val data = articlesDatas[position]
            when(view.id) {
                R.id.fragment_home_recycleview_item_thumbnail -> {
                    if (isLogin) {
                        if (!NetworkUtil.isNetworkConnected(App.context)) {
                            showToast("网络异常")
                            return@OnItemChildClickListener
                        }
                        mPresenter?.removeCollect(data.id, data.originId)
                        collectAdapter.remove(position)
                        EventBus.getDefault().post(CollectEvent(false))
                    } else {
                        Intent(this, LoginActivity::class.java).run {
                            startActivity(this)
                        }
                    }
                }
            }
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogin(event: LoginEvent) {
        mPresenter?.getCollectDatas(0)
    }

}