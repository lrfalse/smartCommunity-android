package com.ysjkj.smart.community.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R
import com.ysjkj.smart.community.mvp.ui.adapter.HomePagerAdapter
import com.ysjkj.smart.community.provider.base.BaseUMengActivity
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.main_activity_home.*
import kotlinx.android.synthetic.main.main_include_home_bottom_tabs.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.onPageChangeListener

/**
 * 主页
 */
class HomeActivity : BaseUMengActivity<EmptyPresenter>() {

    val qrFragment by lazy {
        router(RouterPath.DoorGuard.QR_CODE_FRAGMENT).navigation() as Fragment
    }
    val pwdFragment by lazy {
        router(RouterPath.DoorGuard.PWD_FRAGMENT).navigation() as Fragment
    }

    val shakeFragment by lazy {
        router(RouterPath.DoorGuard.SHAKE_FRAGMENT).navigation() as Fragment
    }

    val remoteFragment by lazy {
        router(RouterPath.DoorGuard.REMOTE_FRAGMENT).navigation() as Fragment
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.main_activity_home)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initTabs()
        initArcMenu()
    }


    /**
     * 初始化底部tab
     */
    private fun initTabs() {
        var tabs = arrayListOf(tx_tab_home, tx_tab_life, tx_tab_neighbor, tx_tab_mine)
        tabs.forEachWithIndex { i, textView ->
            textView.onClick {
                vp_content.setCurrentItem(i, false)
            }
        }
        vp_content.apply {
            adapter = HomePagerAdapter(supportFragmentManager)
            offscreenPageLimit = 4
            onPageChangeListener {
                onPageSelected {
                    tabs.forEachIndexed { index, textView ->
                        textView.isSelected = index == it
                    }
                }
            }
            vp_content.setCurrentItem(0, false)
        }

        tabs.forEachIndexed { index, textView ->
            textView.isSelected = vp_content.currentItem == index
        }
    }

    /**
     * 初始化arc菜单
     */
    private fun initArcMenu() {
        arc_menus.apply {
            onMenuClickListener {
                when (this) {
                    0 -> {
                        //二维码开门
                        replaceFragment(R.id.fl_door_float, qrFragment)
                    }
                    1 -> {
                        replaceFragment(R.id.fl_door_float, pwdFragment)
                    }
                    2 -> {
                        replaceFragment(R.id.fl_door_float, shakeFragment)
                    }
                    3 -> {
                        replaceFragment(R.id.fl_door_float, remoteFragment)
                    }
                }
            }
            onMenuChangeListener { centerIsOpen, subIsShow ->
                if (!centerIsOpen) {
                    removeFragment(qrFragment)
                    removeFragment(pwdFragment)
                    removeFragment(shakeFragment)
                    removeFragment(remoteFragment)
                }
            }
        }
    }


    override fun onBackPressed() {
        if (arc_menus.isOpen()) {
            arc_menus.close()
        } else {
            super.onBackPressed()
        }

    }
}
