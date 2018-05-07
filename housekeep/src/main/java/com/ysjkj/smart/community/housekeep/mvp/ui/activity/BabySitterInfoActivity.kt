package com.ysjkj.smart.community.housekeep.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.hk_activity_baby_sitter_info.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@Route(path = RouterPath.HouseKeep.BABY_SITTER_INFO)
class BabySitterInfoActivity : BaseActivity<EmptyPresenter>() {
    val imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_activity_baby_sitter_info).withToolbar("保姆", true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_head.load(imgUrl)
        vp_info.apply {
            bindFragment(supportFragmentManager, mutableListOf(
                    PagerItem(router(RouterPath.HouseKeep.BABY_SITTER_EXPERIENCE_FRAGMENT).navigation() as Fragment, "服务经验"),
                    PagerItem(router(RouterPath.HouseKeep.BABY_SITTER_TRAIN_FRAGMENT).navigation() as Fragment, "培训背景")
            ))
            tab_info.wrapLine().setupWithViewPager(this)
        }
        tx_more.onClick {
            jump(RouterPath.HouseKeep.BABY_SITTER_APPLY)
        }
    }

}
