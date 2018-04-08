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
 * 红包界面
 * @author yangqiang
 * @create 2018-04-04 下午3:01
 **/
@Route(path = RouterPath.DoorGuard.RED_PACKET_FRAGMENT)
class RedPacketFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.door_red_packet_fragment)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        
    }
}