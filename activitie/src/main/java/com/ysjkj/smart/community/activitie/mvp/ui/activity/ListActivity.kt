package com.ysjkj.smart.community.activitie.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.activitie.R
import com.ysjkj.smart.community.provider.router.RouterPath
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activitie_activity_list.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

@Route(path = RouterPath.Activitie.LIST)
class ListActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.activitie_activity_list).withToolbar("社区活动", true, menuId = R.menu.activitie_menu_list, stytle = R.style.Toolbar_Theme_Pink) {
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        rcl_activite.apply {
            linear(dip(5), R.color.activite_gray_bg)
            dontAnima()
            bindAdapter(mutableListOf<String>().apply { (1..5).forEach { add("") } }, R.layout.activitie_recycle_item_list, mapOf()) { helper, item ->
                loadItem(helper.itemView)
            }
        }

    }

    private fun loadItem(view: View?) {
        view?.apply {

            view.find<ImageView>(R.id.iv_banner).load("http://pic74.nipic.com/file/20150810/21292008_145040212889_2.jpg")
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
