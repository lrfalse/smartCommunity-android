package com.ysjkj.smart.community.door.view

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.piaolac.core.glide.GlideApp
import com.ysjkj.smart.community.door.R
import kotlinx.android.synthetic.main.door_view_red_packet.view.*
import org.jetbrains.anko.include
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 红包
 * @author
 * @create 2018-04-04 下午2:59
 **/
class RedPacketView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        include<View>(R.layout.door_view_red_packet)
        tx_bless.alpha = 0f
        tx_money.alpha = 0f
        tx_save.alpha = 0f
        btn_open.onClick {
            openAnimation()
        }
        GlideApp.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522901343777&di=75636be6dabb50be2b1c79e4ab51ae4e&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F140410%2F10-140410143105L4.jpg").fitCenter().into(iv_ad)
    }


    private fun openAnimation() {

        arrayOf(btn_open, tx_send_tip).forEach {
            it.isEnabled = false
            ObjectAnimator.ofFloat(it, "alpha", 0f).start()
        }

        arrayOf(tx_bless, tx_money, tx_save).forEach {
            it.isEnabled = false
            ObjectAnimator.ofFloat(it, "alpha", 1f).start()
        }

        arrayOf(iv_shop_icon, iv_body, tx_shop, tx_send_tip, tx_bless, tx_money).forEach {
            ObjectAnimator.ofFloat(it, "translationY", 180f).start()
        }

        ObjectAnimator.ofFloat(iv_cap, View.Y, -iv_cap.measuredHeight.toFloat()).start()

    }
}