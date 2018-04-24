package com.ysjkj.smart.community.activitie.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.activitie.R
import com.ysjkj.smart.community.provider.router.RouterPath
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activitie_activity_detail.*
import org.jetbrains.anko.dip

@Route(path = RouterPath.Activitie.DETAIL)
class DetailActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.activitie_activity_detail).withToolbar("社区乐跑赛", true, menuId = R.menu.activitie_menu_detail)
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_activitie.load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524026099051&di=0a6174cb167555110e8aab8c50ca74d7&imgtype=0&src=http%3A%2F%2Fpic6.nipic.com%2F20100412%2F4410655_200439458739_2.jpg")
        rcl_comment.apply {
            dontAnima()
            isNestedScrollingEnabled = false
            linear(0)
            bindAdapter(mutableListOf<String>().apply { (1..3).forEach { add("") } }, R.layout.activitie_recycle_item_comment_list, mapOf())
        }
        rcl_head.apply {
            var data = mutableListOf(
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761986&di=c424a87cfd841a67176cc8f8ed50c331&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Ffile%2F2013%2F2015-5%2F2015051514475586835.png",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524112136152&di=bbb07e44bcd00d5db9b5720bede4a3af&imgtype=0&src=http%3A%2F%2Fwww.yyweishi.com%2Fuploads%2Fallimg%2F140323%2F010H92234-16.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761987&di=79cd3da789dbaf9a53f5835c3cc69232&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D1588b7c5d739b6004dce0fbfd9503526%2F7bec54e736d12f2eb97e1a464dc2d56285356898.jpg")
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

        tx_remark.apply {
            resources.getDrawable(R.drawable.activitie_detail_circle).apply {
                listOf("开始时间：", "结束时间：", "活动地点：", "参加人数：", "活动费用：", "报名截止：", """联  系  人：""", """电　　话：""").forEach {
                    appendDrawable(this)
                    append("""  $it""")
                    append("\n")
                }
            }

        }
    }

}

