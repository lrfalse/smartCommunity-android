package com.ysjkj.smart.community.notice.mvp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.glide.GlideApp
import com.piaolac.core.mvp.EmptyPresenter
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import com.ysjkj.smart.community.notice.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.notice_fragment_banner.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 公告banner
 * @author yangqiang
 * @create 2018-04-17 下午5:21
 **/
@Route(path = RouterPath.Notice.BANNER)
class BannerFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.notice_fragment_banner)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {

        marquee_notice.apply {
            startWithList(mutableListOf("小区4月24号晚上停电", "小区5月3号全天停水", "小区6月2号全天停电"))
            setOnItemClickListener { position, textView ->

            }
        }

        banner_notice.apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            setImages(mutableListOf(
                    "http://pic74.nipic.com/file/20150810/21292008_145040212889_2.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072573&di=183611f1ad68f299c8ded9390a3074fe&imgtype=0&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20140827%2Fsy_69668384381.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"))
            setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                    GlideApp.with(imageView).load(path).into(imageView)
                }
            })
            isAutoPlay(true)
            start()
        }

        tx_more.onClick {
            router(RouterPath.Notice.LIST).navigation()
        }
    }
}