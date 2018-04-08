package com.ysjkj.smart.community.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R

/**
 * Created by yangqiang on 2018/4/3.
 */
class HomeFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.main_fragment_home, true)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
    }
}