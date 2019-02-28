package com.example.mywanandroid.mvp.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.mywanandroid.App
import com.example.mywanandroid.R
import com.example.mywanandroid.adapter.HomeAdapter
import com.example.mywanandroid.constant.Constant
import com.example.mywanandroid.event.CollectEvent
import com.example.mywanandroid.event.LoginEvent
import com.example.mywanandroid.ext.load
import com.example.mywanandroid.mvp.base.BaseMvpFragment
import com.example.mywanandroid.mvp.contract.HomeContract
import com.example.mywanandroid.mvp.model.bean.Article
import com.example.mywanandroid.mvp.model.bean.ArticleList
import com.example.mywanandroid.mvp.model.bean.Banner
import com.example.mywanandroid.mvp.presenter.HomePresenter
import com.example.mywanandroid.utils.NetworkUtil
import com.example.mywanandroid.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.longToast

/**
 * created by chenhanbin on 2019/2/25 15:49
 *
 */
class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View{

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    /**
     * 文章数据
     */
    private val articlesDatas = mutableListOf<Article>()

    /**
     * banner数据
     */
    private var bannersDatas = arrayListOf<Banner>()

    /**
     * 顶部banner视图控件
     */
    private var bannerView:View? = null

    /**
     * 是否刷新，区别刷新和加载更多
     */
    private var isRefresh = true

    /**
     * 文章适配器
     */
    private val homeAdapter: HomeAdapter by lazy{
        HomeAdapter(activity, articlesDatas)
    }

    /**
     * Banner 适配器
     */
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            imageView.load(activity, feedImageUrl)
        }
    }


    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }


    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun layoutId(): Int = R.layout.fragment_home

    override fun lazyLoad() {
        mPresenter?.requestHomeData()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
        fragment_home_swip_refresh_layout.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showToast(msg: String) {
        longToast(msg)
    }

    override fun showError() {

    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    override fun initView(view: View) {
        Log.d("chenhanbin", "Fragment initview")
        super.initView(view)
        fragment_home_swip_refresh_layout.setOnRefreshListener {
            isRefresh = true
            homeAdapter.setEnableLoadMore(false)
            mPresenter?.requestHomeData()
        }
        fragment_home_recycleview.run {
            layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
//            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
        homeAdapter.run {
            bindToRecyclerView(fragment_home_recycleview)
            setOnLoadMoreListener(onLoadMoreListener, fragment_home_recycleview)
            onItemClickListener = this@HomeFragment.onItemClickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener

        }

    }


    override fun scrollToTop() {
        fragment_home_recycleview.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }

    }

    override fun setBanner(banners: List<Banner>) {

    }

    override fun setArticlesList(articleList: ArticleList) {
        Log.d("chenhanbin", "articleList = " + articleList)
        articleList.datas.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < articleList.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
    }

    private val onLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener{
        fragment_home_swip_refresh_layout.isRefreshing = false
        val pageNum = homeAdapter.data.size / 20
        mPresenter?.requireArticlesList(pageNum)
        isRefresh = false
    }

    private val onItemClickListener =  BaseQuickAdapter.OnItemClickListener{adapter, view, position ->
        if (articlesDatas.size != 0) {
            val data = articlesDatas[position]
//            展示详情
            Intent(activity, DetailsActivity::class.java).run {
                putExtra(Constant.KEY_DETAIL_ID, data.id)
                putExtra(Constant.KEY_DETAIL_URL_KEY, data.link)
                putExtra(Constant.KEY_DETAIL_TITLE, data.title)
                putExtra(Constant.KEY_DETAIL_COLLECT, data.collect)
                startActivity(this)
            }
        }

    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener{adapter, view, position ->
        if (articlesDatas.size > 0) {
            val data = articlesDatas[position]
            when(view.id) {
                R.id.fragment_home_recycleview_item_thumbnail -> {
                    if (isLogin) {
                        if (!NetworkUtil.isNetworkConnected(App.context)) {
                            showToast("网络异常")
                            return@OnItemChildClickListener
                        }
                        Log.d("chenhanbin", "fragment_home_recycleview_item_thumbnail")
                        Log.d("chenhanbin", "data.collect = " + data.collect)
                        Log.d("chenhanbin", "data.id = " + data.id)
                        if (data.collect) {
                            mPresenter?.cancelCollect(data.id)
                        } else {
                            mPresenter?.addCollect(data.id)
                        }
                        data.collect = !data.collect
                        homeAdapter.setData(position, data)
                    } else {
                        Intent(activity, LoginActivity::class.java).run {
                            startActivity(this)
                        }
                    }
                }
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCollect(event: CollectEvent) {
        mPresenter?.requestHomeData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogin(event: LoginEvent) {
        mPresenter?.requestHomeData()
    }

}