package com.ysjkj.smart.community.life.mvp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.library.CircleImageView
import com.allen.library.SuperTextView
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import com.ysjkj.smart.community.life.R
import com.ysjkj.smart.community.provider.ext.gallery
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.life_include_view_business_activities.*
import kotlinx.android.synthetic.main.life_include_view_business_banner.*
import kotlinx.android.synthetic.main.life_include_view_business_menu.*
import kotlinx.android.synthetic.main.life_include_view_business_recommend.*

/**
 * 商圈
 * @author yangqiang
 * @create 2018-04-23 上午10:54
 **/
@Route(path = RouterPath.Life.BUSINESS_FRAGMENT)
class BusinessFragment : BaseFragment<EmptyPresenter>() {
    val imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.life_fragment_business)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        initBanner()
        initMenu()
        initActivitie()
        initRecommend()
    }

    /**
     * 初始化banner
     */
    private fun initBanner() {
        banner_business.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            setImages(mutableListOf(
                    "http://pic74.nipic.com/file/20150810/21292008_145040212889_2.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072573&di=183611f1ad68f299c8ded9390a3074fe&imgtype=0&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20140827%2Fsy_69668384381.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"))
            setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
                    imageView?.load(path)
                }
            })
            isAutoPlay(true)
            start()
        }
    }

    /**
     * 初始化菜单
     */
    private fun initMenu() {
        val data = listOf(Menu("超市", R.drawable.life_business_menu_supermarket),
                Menu("美食", R.drawable.life_business_menu_food),
                Menu("娱乐", R.drawable.life_business_menu_entertainment),
                Menu("旅游", R.drawable.life_business_menu_tourism),
                Menu("住宿", R.drawable.life_business_menu_hotel),
                Menu("药店", R.drawable.life_business_menu_pharmacy),
                Menu("更多", R.drawable.life_business_menu_more))
        rcl_menu.apply {
            grid(4)
            bindAdapter(data, R.layout.life_recycle_item_business_menu, mapOf()) { helper, item ->
                helper.setImageResource(R.id.iv_icon, item.icon)
                helper.setText(R.id.tx_title, item.title)
            }.setOnItemClickListener { adapter, view, position ->

            }
            isNestedScrollingEnabled = false
        }

    }

    /**
     * 初始化活动
     */
    private fun initActivitie() {
        rcl_activitie.apply {
            dontAnima()
            gallery()
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            bindAdapter(mutableListOf<String>().apply { (1..5).forEach { add("") } }, R.layout.life_recycle_item_business_activitie, mapOf()) { helper, _ ->
                helper.getView<SuperTextView>(R.id.stx_business).rightTextView.apply {
                    appendFontSpan(1.5f, "9.8折")
                    setColorSpan(resources.getColor(R.color.colorAccent), 2)
                }
                helper.getView<ImageView>(R.id.iv_ad).load(imgUrl) {
                    centerCrop()
                }
            }
            tx_page_index.text = "1/"
            tx_page_index.appendFontSpan(0.5f, "${this.adapter.itemCount}")
            addOnPageChangedListener { i, j ->
                tx_page_index.text = "${j + 1}/"
                tx_page_index.appendFontSpan(0.5416f, "${this.adapter.itemCount}")
            }
        }
    }

    /**
     * 初始化推荐
     */
    private fun initRecommend() {
        rcl_recommend.apply {
            dontAnima()
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            linear(0, orientation = LinearLayoutManager.HORIZONTAL)
            bindAdapter(mutableListOf<String>().apply { (1..5).forEach { add("") } }, R.layout.life_recycle_item_business_recommend, mapOf()) { helper, item ->
                helper.getView<CircleImageView>(R.id.iv_ad).load(imgUrl)
            }
        }
        iv_recommend.load(imgUrl)
    }

    data class Menu(val title: String, val icon: Int)
}