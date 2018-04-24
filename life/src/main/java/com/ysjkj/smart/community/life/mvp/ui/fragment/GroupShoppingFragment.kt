package com.ysjkj.smart.community.life.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.life.R
import com.ysjkj.smart.community.provider.router.RouterPath

/**
 * 邻居一起来 (团购)
 * @author yangqiang
 * @create 2018-04-24 下午4:45
 **/
@Route(path = RouterPath.Life.GROUP_SHOPPING_FRAGMENT)
class GroupShoppingFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.life_fragment_group_shopping)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
    }
}