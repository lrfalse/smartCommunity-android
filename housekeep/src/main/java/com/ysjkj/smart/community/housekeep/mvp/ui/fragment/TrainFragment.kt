package com.ysjkj.smart.community.housekeep.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.dontAnima
import com.piaolac.core.ext.linear
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.hk_fragment_train.*

/**
 * 培训背景
 * @author yangqiang
 * @create 2018-04-25 下午9:52
 **/
@Route(path = RouterPath.HouseKeep.BABY_SITTER_TRAIN_FRAGMENT)
class TrainFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_fragment_train)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        rcl_train.apply {
            dontAnima()
            linear(0)
            bindAdapter(mutableListOf<Int>().apply { (1..5).forEach { add(1) } }, R.layout.hk_recycle_item_train, mapOf())
        }
    }
}