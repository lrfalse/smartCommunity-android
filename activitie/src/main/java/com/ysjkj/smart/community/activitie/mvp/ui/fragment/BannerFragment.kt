package com.ysjkj.smart.community.activitie.mvp.ui.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoaderInterface
import com.ysjkj.smart.community.activitie.R
import com.ysjkj.smart.community.provider.router.RouterPath
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activitie_fragment_banner.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.dip

/**
 * 活动banner
 * @author yangqiang
 * @create 2018-04-18 下午4:17
 **/
@Route(path = RouterPath.Activitie.BANNER_FRAGMENT)
class BannerFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.activitie_fragment_banner)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {

        tx_page_index.onClick {
            jump(RouterPath.Activitie.LIST)
        }


        banner_activitie.apply {
            var data = mutableListOf(
                    "http://pic74.nipic.com/file/20150810/21292008_145040212889_2.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072573&di=183611f1ad68f299c8ded9390a3074fe&imgtype=0&src=http%3A%2F%2Fimg05.tooopen.com%2Fimages%2F20140827%2Fsy_69668384381.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg")
            setBannerStyle(BannerConfig.NOT_INDICATOR)
            setIndicatorGravity(BannerConfig.CENTER)
            setImages(data)
            setImageLoader(object : ImageLoaderInterface<View> {
                override fun createImageView(context: Context?) = View.inflate(context, R.layout.activitie_banner_item, null)

                override fun displayImage(context: Context?, path: Any, view: View) {

                    loadItem(view)
                }
            })
            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    tx_page_index.text = "${position + 1}/"
                    tx_page_index.appendFontSpan(0.5416f, "${data.size}")
                }

            })
            start()
            stopAutoPlay()
        }

    }


    private fun loadItem(view: View?) {
        view?.apply {
            view.findViewById<ImageView>(R.id.iv_banner).load("http://pic74.nipic.com/file/20150810/21292008_145040212889_2.jpg")
            view.find<RecyclerView>(R.id.rcl_head).apply {
                var data = mutableListOf(
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761986&di=c424a87cfd841a67176cc8f8ed50c331&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Ffile%2F2013%2F2015-5%2F2015051514475586835.png",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524112136152&di=bbb07e44bcd00d5db9b5720bede4a3af&imgtype=0&src=http%3A%2F%2Fwww.yyweishi.com%2Fuploads%2Fallimg%2F140323%2F010H92234-16.jpg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761987&di=79cd3da789dbaf9a53f5835c3cc69232&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D1588b7c5d739b6004dce0fbfd9503526%2F7bec54e736d12f2eb97e1a464dc2d56285356898.jpg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761987&di=a77f8d0e647db0bb1e98bc95878c1372&imgtype=0&src=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-05-14%2F051120936.jpg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524115930134&di=f9cd8c9bb0a18d700528764394ffb329&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201408%2F25%2F20140825003932_x3ETJ.jpeg")
                dontAnima()
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        if (parent?.getChildAdapterPosition(view) != 0) {
                            outRect?.right = -dip(5)
                        }
                    }
                })
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true).apply {
                    scrollToPositionWithOffset(data.size - 1, 0)
                }
                bindAdapter(data, R.layout.activitie_recycle_item_head, mapOf()) { helper, item ->
                    helper.getView<CircleImageView>(R.id.iv_head).load(item)
                }
            }
            view.find<TextView>(R.id.tx_more).onClick {
                jump(RouterPath.Activitie.DETAIL)
            }
        }
    }

}