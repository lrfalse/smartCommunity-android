package com.ysjkj.smart.community.notice.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.library.SuperTextView
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.dontAnima
import com.piaolac.core.ext.linear
import com.piaolac.core.ext.router
import com.piaolac.core.glide.GlideApp
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.notice.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.notice_activity_list.*

@Route(path = RouterPath.Notice.LIST)
class ListActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.notice_activity_list).withToolbar("小区公告", true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        rcl_notice.apply {
            dontAnima()
            linear(0, R.color.notice_transparent)
            bindAdapter(mutableListOf<String>().apply { (1 until 10).forEach { add(it.toString()) } }, R.layout.notice_recycle_item_list, mapOf()) { helper, item ->
                GlideApp.with(this@ListActivity).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524026099051&di=0a6174cb167555110e8aab8c50ca74d7&imgtype=0&src=http%3A%2F%2Fpic6.nipic.com%2F20100412%2F4410655_200439458739_2.jpg")
                        .fitCenter().into(helper.getView(R.id.iv_notice))
                helper.getView<SuperTextView>(R.id.stx_more).setOnSuperTextViewClickListener {
                    router(RouterPath.Notice.DETAIL).navigation()
                }
            }
        }
    }

}
