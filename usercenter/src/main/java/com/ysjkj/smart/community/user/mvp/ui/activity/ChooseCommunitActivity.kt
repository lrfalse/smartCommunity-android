package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseViewHolder
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.linear
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import kotlinx.android.synthetic.main.user_activity_choose_communit.*

/**
 * 选择社区
 */
@Route(path = RouterPath.UserCenter.CHOOSE_COMMUNIT)
class ChooseCommunitActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_choose_communit).withToolbar("", true)

    }

    override fun initView(savedInstanceState: Bundle?) {

        rv_communits.apply {
            var selectIndex = -1
            linear()
            bindAdapter(mutableListOf<String>().apply { (1..3).forEach { add("社区$it") } }, R.layout.user_recycle_item_simple, mapOf(R.id.tx_title to "")) { baseViewHolder: BaseViewHolder, s: String ->
                baseViewHolder.getView<TextView>(R.id.tx_title).setCompoundDrawablesWithIntrinsicBounds(0, 0, if (baseViewHolder.adapterPosition == selectIndex) R.drawable.user_choose else 0, 0)
                baseViewHolder.setGone(R.id.tx_head, baseViewHolder.adapterPosition == 0)
                baseViewHolder.setGone(R.id.tx_line, baseViewHolder.adapterPosition == 0)
            }.apply {
                setOnItemClickListener { _, _, position ->
                    selectIndex = position
                    notifyDataSetChanged()
                    router(RouterPath.UserCenter.SET_HOUSE_INFO).navigation()
                }
            }
        }
    }

}
