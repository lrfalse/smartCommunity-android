package com.piaolac.core.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.piaolac.core.R
import com.piaolac.core.utils.ReflectionUtils
import com.piaolac.core.view.HorizontalDivider
import com.piaolac.core.view.VerticalDivider
import org.jetbrains.anko.dip

/**
 * Created by yangqiang on 2018/3/14.
 */


val matchParent: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
val wrapContent: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
val allMathParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

fun Any.router(url: String, block: Postcard.() -> Unit = {}) = ARouter.getInstance().build(url).apply(block)

fun Any.router(url: Uri, block: Postcard.() -> Unit = {}) = ARouter.getInstance().build(url).apply(block)

fun Context.mipmapRes(name: String) = identifier(name, "mipmap")

fun Context.arrayRes(context: Context, name: String) = identifier(name, "array")

fun Context.drawableRes(name: String) = identifier(name, "drawable")

fun Context.identifier(name: String, type: String) = resources.getIdentifier(name, type, packageName)


fun <T> Any?.ifNotNull(t: T.() -> Unit, f: () -> Unit = {}) {
    if (this != null) {
        t.invoke(this as T)
    } else {
        f.invoke()
    }
}

fun Context.statusBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        // 获得状态栏高度
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
    return 0
}

fun Activity.translucentStatus(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity.window.statusBarColor = Color.TRANSPARENT
    } else {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}


inline fun RecyclerView.grid(spanCount: Int, size: Int = 0, colorRes: Int = R.color.white) {
    layout(GridLayoutManager(context, spanCount), size, colorRes)
}

inline fun RecyclerView.linear(size: Int = dip(0.5f), colorRes: Int = R.color.white) {
    layout(LinearLayoutManager(context), size, colorRes)
}


inline fun RecyclerView.layout(layout: RecyclerView.LayoutManager, size: Int = 0, colorRes: Int = R.color.white) {
    layoutManager = layout
    addItemDecoration(HorizontalDivider.Builder(context).size(size).colorResId(colorRes).build())
    if (layout is GridLayoutManager) {
        addItemDecoration(VerticalDivider.Builder(context).size(size).colorResId(colorRes).build())
    }
}

inline fun RecyclerView.dontAnima() {
    itemAnimator.addDuration = 0
    itemAnimator.changeDuration = 0
    itemAnimator.moveDuration = 0
    itemAnimator.removeDuration = 0
}

inline fun <T : Any> RecyclerView.bindAdapter(data: List<T>, layoutId: Int, bind: Map<Int, String>, crossinline convert: (BaseViewHolder, T) -> Unit = { _: BaseViewHolder, _: T -> }): BaseQuickAdapter<T, BaseViewHolder> {
    adapter = object : BaseQuickAdapter<T, BaseViewHolder>(layoutId, data) {
        override fun convert(helper: BaseViewHolder, item: T) {
            bind.forEach {
                helper.getView<View>(it.key)?.apply {
                    when (this) {
                        is TextView -> if (it.value.isEmpty()) text = item.toString() else
                            ReflectionUtils.getField(item, it.value)?.apply { text = toString() }
                    }
                }
            }
            convert.invoke(helper, item)
        }
    }
    return adapter as BaseQuickAdapter<T, BaseViewHolder>
}

