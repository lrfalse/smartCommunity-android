package com.ysjkj.smart.community.housekeep.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.router.RouterPath

@Route(path = RouterPath.HouseKeep.BABY_SITTER_APPLY)
class BabySitterApplyActivity : BaseActivity<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_activity_baby_sitter_apply).withToolbar("资料填写", true)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

}
