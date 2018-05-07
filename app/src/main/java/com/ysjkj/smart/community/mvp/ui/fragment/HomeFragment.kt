package com.ysjkj.smart.community.mvp.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.main_fragment_home.*

/**
 * Created by yangqiang on 2018/4/3.
 */
class HomeFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.main_fragment_home, true, true)
                .withToolbar(menuId = R.menu.main_menu_home_community)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        sp_communit.apply {
            adapter = ArrayAdapter<String>(activity, R.layout.main_spinner_item_simple, R.id.tx_title, mutableListOf("阳光小镇", "阳光小镇2", "阳光小3"))
        }

        (router(RouterPath.Notice.BANNER_FRAGMENT).navigation() as? Fragment)?.apply {
            addFragment(R.id.fl_notice, this)
        }
        (router(RouterPath.Main.HOME_MENU_FRAGMENT).navigation() as? Fragment)?.apply {
            addFragment(R.id.fl_menu, this)
        }

        (router(RouterPath.Activitie.BANNER_FRAGMENT).navigation() as? Fragment)?.apply {
            addFragment(R.id.fl_activite, this)
        }
    }
}