package com.ysjkj.smart.community.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.ysjkj.smart.community.mvp.ui.fragment.HomeFragment

/**
 * Created by yangqiang on 2018/4/3.
 */
class HomePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
//        return router(RouterPath.DoorGuard.RED_PACKET_FRAGMENT).navigation() as Fragment
        return HomeFragment()
    }

    override fun getCount() = 4
}