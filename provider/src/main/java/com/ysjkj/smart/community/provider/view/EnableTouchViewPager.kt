package com.ysjkj.smart.community.provider.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by yangqiang on 2018/4/3.
 */
class EnableTouchViewPager @JvmOverloads constructor(context: Context?, attrs: AttributeSet?) : ViewPager(context, attrs) {

    var isTouch = false
    override fun onTouchEvent(ev: MotionEvent?) = isTouch

    override fun onInterceptTouchEvent(ev: MotionEvent?) = isTouch

    fun enableTouch(isTouch: Boolean) {
        this.isTouch = isTouch
    }
}