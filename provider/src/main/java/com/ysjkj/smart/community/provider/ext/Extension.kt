package com.ysjkj.smart.community.provider.ext

import android.support.v7.widget.RecyclerView
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager

/**
 *
 * @author yangqiang
 * @create 2018-04-23 下午6:14
 **/


fun RecyclerViewPager.gallery() {
    setHasFixedSize(true)
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val childCount = childCount
            val width = getChildAt(0).width
            val padding = (getWidth() - width) / 2
            val offset = padding.toFloat() / measuredWidth
            for (j in 0 until childCount) {
                val v = getChildAt(j)
                var rate = 0f
                if (v.left <= padding) {
                    if (v.left >= padding - v.width) {
                        rate = (padding - v.left) * 1f / v.width
                    } else {
                        rate = 1f
                    }
                    v.scaleX = 1 - rate * offset

                } else {
                    if (v.left <= getWidth() - padding) {
                        rate = (getWidth() - padding - v.left) * 1f / v.width
                    }
                    v.scaleX = (1 - offset) + rate * offset
                }
            }
        }
    })
}