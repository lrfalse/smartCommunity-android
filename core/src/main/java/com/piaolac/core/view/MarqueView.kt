package com.piaolac.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * 循环显示的textView
 * @author yangqiang
 * @create 2018-04-16 下午6:09
 **/
class MarqueView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    override fun isFocused() = true
}