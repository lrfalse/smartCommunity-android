package com.ysjkj.smart.community.door.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.door.R
import com.ysjkj.smart.community.provider.router.RouterPath

/**
 * 摇一摇开门
 * @author yangqiang
 * @create 2018-04-08 上午10:34
 **/
@Route(path = RouterPath.DoorGuard.SHAKE_FRAGMENT)
class ShakeFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.door_shake_fragment)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
    }
}