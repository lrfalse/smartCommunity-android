package com.ysjkj.smart.community.mvp.ui.activity

import android.os.Bundle
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R
import com.ysjkj.smart.community.provider.router.RouterPath

class LaunchActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.activity_launch)
//        router(RouterPath.UserCenter.WELCOME).navigation()
        router(RouterPath.Main.HOME).navigation()
        finish()
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

}
