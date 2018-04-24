package com.ysjkj.smart.community.notice.mvp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.dontAnima
import com.piaolac.core.ext.linear
import com.piaolac.core.glide.GlideApp
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.notice.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.notice_activity_detail.*

@Route(path = RouterPath.Notice.DETAIL)
class DetailActivity : BaseActivity<EmptyPresenter>() {

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.notice_activity_detail).withToolbar("详情", true, menuId = R.menu.notice_menu_detail)
    }

    override fun initView(savedInstanceState: Bundle?) {
        GlideApp.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524026099051&di=0a6174cb167555110e8aab8c50ca74d7&imgtype=0&src=http%3A%2F%2Fpic6.nipic.com%2F20100412%2F4410655_200439458739_2.jpg").centerCrop().into(iv_notice)
        rcl_comment.apply {
            dontAnima()
            isNestedScrollingEnabled=false
            linear(0)
            bindAdapter(mutableListOf<String>().apply { (1..10).forEach { add("") } }, R.layout.notice_recycle_item_comment_list, mapOf())
        }
    }

}
