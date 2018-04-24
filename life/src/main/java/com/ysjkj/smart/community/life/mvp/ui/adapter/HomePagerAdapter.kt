package com.ysjkj.smart.community.life.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.piaolac.core.ext.router
import com.ysjkj.smart.community.provider.router.RouterPath

/**
 *
 * @author yangqiang
 * @create 2018-04-23 上午10:53
 **/
class HomePagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    val titles = listOf("商圈", "邻居一起来", "鲤市")
    val fragments = listOf(router(RouterPath.Life.BUSINESS_FRAGMENT).navigation() as Fragment,
            router(RouterPath.Life.GROUP_SHOPPING_FRAGMENT).navigation() as Fragment,
            router(RouterPath.Life.BUSINESS_FRAGMENT).navigation() as Fragment)

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = titles.size

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}