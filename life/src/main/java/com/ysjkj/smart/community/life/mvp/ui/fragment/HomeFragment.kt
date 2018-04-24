package com.ysjkj.smart.community.life.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.wrapLine
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.life.R
import com.ysjkj.smart.community.life.mvp.ui.adapter.HomePagerAdapter
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.life_fragment_home.*


/**
 * 生活首页
 * @author yangqiang
 * @create 2018-04-23 上午10:30
 **/
@Route(path = RouterPath.Life.HOME_FRAGMENT)
class HomeFragment : BaseFragment<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.life_fragment_home)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        vp_content.apply {
            adapter = HomePagerAdapter(childFragmentManager)
        }
        tab_group.apply {
            setupWithViewPager(vp_content)
            wrapLine()
        }

    }
}