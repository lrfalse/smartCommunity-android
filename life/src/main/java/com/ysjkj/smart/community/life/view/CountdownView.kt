package com.ysjkj.smart.community.life.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.ysjkj.smart.community.life.R
import com.ysjkj.smart.community.provider.utils.DateUtils
import kotlinx.android.synthetic.main.view_countdown_time.view.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.include


/**
 * 倒计时控件
 * @author yangqiang
 * @create 2018-04-24 下午5:31
 **/
class CountdownView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        include<View>(R.layout.view_countdown_time)
    }

    fun expiration(time: Long) {
        DateUtils.formatCountdownTime(time, "dd-hh-mm").split("-").forEachWithIndex { i, s ->
            when (i) {
                0 -> tx_day.text = s
                1 -> tx_hour.text = s
                2 -> tx_minute.text = s
            }
        }
    }

}