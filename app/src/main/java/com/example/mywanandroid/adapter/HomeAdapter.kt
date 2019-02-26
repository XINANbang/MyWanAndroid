package com.example.mywanandroid.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.mywanandroid.R
import com.example.mywanandroid.ext.load
import com.example.mywanandroid.mvp.model.bean.Article

/**
 * created by chenhanbin on 2019/2/26 12:03
 *
 */
class HomeAdapter(private val context: FragmentActivity?, datas: MutableList<Article>):
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_fragment_home, datas){

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        item ?: return
        helper ?: return

        Log.d("chenhanbin", "-------------------------------")
        Log.d("chenhanbin", "item.title = " + item.title)
        Log.d("chenhanbin", "item.niceDate = " + item.niceDate)
        Log.d("chenhanbin", "getChapterName(item) = " + getChapterName(item))
        Log.d("chenhanbin", "item.envelopePic = " + item.envelopePic)
        Log.d("chenhanbin", "-------------------------------")
        //标题、日期、收藏
        helper.setText(R.id.fragment_home_recycleview_item_title, item.title)
            .setText(R.id.fragment_home_recycleview_item_date, item.niceDate)
            .setImageResource(R.id.fragment_home_recycleview_item_thumbnail, if (item.collect) R.drawable.ic_collect else R.drawable.ic_uncollect)
            .addOnClickListener(R.id.fragment_home_recycleview_item_thumbnail)

        //作者和目录
        helper.setText(R.id.fragment_home_recycleview_item_chapterName, getChapterName(item))

        //预览图
        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.fragment_home_recycleview_item_preimage).visibility = View.VISIBLE
            helper.getView<ImageView>(R.id.fragment_home_recycleview_item_preimage).load(context, item.envelopePic)
        } else {
            helper.getView<ImageView>(R.id.fragment_home_recycleview_item_preimage).visibility = View.GONE
        }

        //置顶 标签
        if (item.isTop == "1") {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag_top).visibility = View.VISIBLE
        } else {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag_top).visibility = View.GONE
        }

        //新 标签
        if (item.fresh) {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag_new).visibility = View.VISIBLE
        } else {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag_new).visibility = View.GONE
        }

        //其他标签
        if (item.tags.size > 0) {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag).text = item.tags[0].name
        } else {
            helper.getView<TextView>(R.id.fragment_home_recycleview_item_tag).visibility = View.GONE
        }

    }

    fun getChapterName(article: Article): String{
        return when {
            article.superChapterName.isNotEmpty() and article.chapterName.isNotEmpty() -> "${article.superChapterName}/${article.chapterName}"
            article.superChapterName.isNotEmpty() -> "${article.chapterName}"
            article.chapterName.isNotEmpty() -> "${article.superChapterName}"
            else -> ""
        }
    }
}