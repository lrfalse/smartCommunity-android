package com.ysjkj.smart.community.provider.view

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 解决嵌套ViewPager滑动冲突
 * @author yangqiang
 * @create 2018-04-26 下午2:38
 **/
class SteadyNestedscrollview @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {
    var isNestedScroll = true
    var xDistance = 0f
    var yDistance = 0f
    var xLast = 0f
    var yLast = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                xDistance = 0f
                yDistance = 0f
                xLast = ev.x
                yLast = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                var curX = ev.x
                var curY = ev.y
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX
                yLast = curY
                if (xDistance > yDistance) {
                    return false
                }

                return isNestedScroll

            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}