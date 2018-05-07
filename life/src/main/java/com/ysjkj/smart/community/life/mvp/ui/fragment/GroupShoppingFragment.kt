package com.ysjkj.smart.community.life.mvp.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.dontAnima
import com.piaolac.core.ext.linear
import com.piaolac.core.ext.load
import com.ysjkj.smart.community.life.R
import com.ysjkj.smart.community.life.mvp.contract.GroupShoppingContract
import com.ysjkj.smart.community.life.mvp.presenter.GroupShoppingPresenter
import com.ysjkj.smart.community.life.view.CountdownView
import com.ysjkj.smart.community.provider.router.RouterPath
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.life_fragment_group_shopping.*
import org.jetbrains.anko.dip
import java.util.*

/**
 * 邻居一起来 (团购)
 * @author yangqiang
 * @create 2018-04-24 下午4:45
 **/
@Route(path = RouterPath.Life.GROUP_SHOPPING_FRAGMENT)
class GroupShoppingFragment : BaseFragment<GroupShoppingPresenter>(), GroupShoppingContract.View {
    val imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"
    var goodsData = mutableListOf<Goods>().apply { (1..15).forEach { add(Goods(1000 * (Random().nextInt(60) * 60 + 60 * 60 * 24 * (Random().nextInt(15) + 1)).toLong())) } }
    override fun onTimeTick(count: Long) {

        goodsData.forEach {
            it.expirationime -= 1000
        }
        rcl_shopping.adapter.notifyDataSetChanged()
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.life_fragment_group_shopping)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        rcl_shopping.apply {
            dontAnima()
            linear(0)
            bindAdapter(goodsData, R.layout.life_recycle_item_shopping_goods, mapOf()) { helper, item ->
                helper.getView<CircleImageView>(R.id.iv_goods).load(imgUrl)
                helper.getView<CountdownView>(R.id.ct_timer).expiration(item.expirationime)
                loadHead(helper.getView(R.id.rcl_head))
            }
        }

        presenter {
            countdownTimer()
        }
    }

    private fun loadHead(view: RecyclerView?) {
        view?.apply {
            var data = mutableListOf(
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761986&di=c424a87cfd841a67176cc8f8ed50c331&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Ffile%2F2013%2F2015-5%2F2015051514475586835.png",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524111761987&di=79cd3da789dbaf9a53f5835c3cc69232&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D1588b7c5d739b6004dce0fbfd9503526%2F7bec54e736d12f2eb97e1a464dc2d56285356898.jpg",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524115930134&di=f9cd8c9bb0a18d700528764394ffb329&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201408%2F25%2F20140825003932_x3ETJ.jpeg")
            dontAnima()
            (0 until itemDecorationCount).forEach {
                getItemDecorationAt(it)?.apply {
                    removeItemDecoration(this)
                }
            }

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
            bindAdapter(data, R.layout.life_recycle_item_head, mapOf()) { helper, item ->
                helper.getView<CircleImageView>(R.id.iv_head).load(item)
            }
        }
    }

    data class Goods(var expirationime: Long)
}