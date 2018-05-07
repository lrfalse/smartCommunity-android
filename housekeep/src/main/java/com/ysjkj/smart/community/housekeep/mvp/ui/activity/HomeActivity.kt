package com.ysjkj.smart.community.housekeep.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.PagerItem
import com.piaolac.core.ext.bindFragment
import com.piaolac.core.ext.router
import com.piaolac.core.ext.wrapLine
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.hk_activity_home.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

@Route(path = RouterPath.HouseKeep.HOME)
class HomeActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_activity_home).withToolbar("家政", true)
        LogUtils.d("${dip(18)} ${sp(18)} ${resources.displayMetrics.density} ${resources.displayMetrics.densityDpi}")
    }

    override fun initView(savedInstanceState: Bundle?) {

        vp_content.apply {
            bindFragment(supportFragmentManager, mutableListOf(
                    PagerItem(router(RouterPath.HouseKeep.BABY_SITTER_FRAGMENT).navigation() as Fragment, "保姆"),
                    PagerItem(router(RouterPath.HouseKeep.CLEAN_FRAGMENT).navigation() as Fragment, "清洁"))
            )
            tab_group.wrapLine().setupWithViewPager(this)
        }
    }

}
