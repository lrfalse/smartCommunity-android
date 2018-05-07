package com.ysjkj.smart.community.provider.ext

import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager
import com.ysjkj.smart.community.provider.R
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk25.coroutines.onClick

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

fun Context.confirmDialog(title: CharSequence, leftBtnText: CharSequence = "否",
                          rightBtnText: CharSequence = "是", autoDismiss: Boolean = true,
                          leftClick: AlertDialog.() -> Unit = {}, rightClick: AlertDialog.() -> Unit = {}) {

    layoutInflater.inflate(R.layout.provider_view_dialog_confirm, null)
            .apply {
                with(AlertDialog.Builder(this@confirmDialog)
                        .setView(this).show()) {
                    find<TextView>(R.id.tx_title).apply {
                        text = title
                    }
                    find<Button>(R.id.btn_left).apply {
                        text = leftBtnText
                        onClick {
                            leftClick.invoke(this@with)
                            if (autoDismiss) {
                                dismiss()
                            }
                        }
                    }
                    find<Button>(R.id.btn_right).apply {
                        text = rightBtnText
                        onClick {
                            rightClick.invoke(this@with)
                            if (autoDismiss) {
                                dismiss()
                            }
                        }
                    }
                }

            }

}

fun Fragment.confirmDialog(title: CharSequence, leftBtnText: CharSequence = "否",
                           rightBtnText: CharSequence = "是", autoDismiss: Boolean = true,
                           leftClick: AlertDialog.() -> Unit = {}, rightClick: AlertDialog.() -> Unit = {}) {
    context?.confirmDialog(title, leftBtnText, rightBtnText, autoDismiss, leftClick, rightClick)
}

fun Context.loadingDialog(backCancel: Boolean = true, outCancel: Boolean = true, dismissListener: () -> Unit = {}): AlertDialog {
    return AlertDialog.Builder(this, R.style.Provider_AlertDialog)
            .setView(layoutInflater.inflate(R.layout.provider_view_loading, null))
            .create()
            .let {
                it.setCancelable(backCancel)
                it.setCanceledOnTouchOutside(outCancel)
                it.setOnDismissListener(DialogInterface.OnDismissListener {
                    dismissListener.invoke()
                })
                it.window.attributes.dimAmount = 0f
                it.show()
                it
            }
}

fun Fragment.loadingDialog(backCancel: Boolean = true, outCancel: Boolean = true, dismissListener: () -> Unit = {}): AlertDialog? {
    return context?.loadingDialog(backCancel, outCancel, dismissListener)
}