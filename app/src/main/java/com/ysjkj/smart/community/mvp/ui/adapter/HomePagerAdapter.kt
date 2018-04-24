package com.ysjkj.smart.community.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.piaolac.core.ext.router
import com.ysjkj.smart.community.mvp.ui.fragment.HomeFragment
import com.ysjkj.smart.community.provider.router.RouterPath

/**
 * Created by yangqiang on 2018/4/3.
 */
class HomePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    val fragments = listOf(HomeFragment(), router(RouterPath.Life.HOME_FRAGMENT).navigation() as Fragment, HomeFragment(), HomeFragment())
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = 4
}