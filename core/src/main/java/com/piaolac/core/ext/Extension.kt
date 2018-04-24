package com.piaolac.core.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.design.widget.TabLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.piaolac.core.R
import com.piaolac.core.glide.GlideApp
import com.piaolac.core.utils.ReflectionUtils
import com.piaolac.core.view.HorizontalDivider
import com.piaolac.core.view.VerticalDivider
import com.piaolac.core.view.VerticalImageSpan
import org.jetbrains.anko.dip

/**
 * Created by yangqiang on 2018/3/14.
 */


val matchParent: Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
val wrapContent: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT
val allMathParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(matchParent, matchParent)

fun Any.router(url: String, block: Postcard.() -> Unit = {}) = ARouter.getInstance().build(url).apply(block)

fun Any.router(url: Uri, block: Postcard.() -> Unit = {}) = ARouter.getInstance().build(url).apply(block)

fun Any.jump(url: String, context: Context? = null, reqCode: Int = -1, callBack: NavigationCallback? = null, block: Postcard.() -> Unit = {}) {
    if (context == null) {
        ARouter.getInstance().build(url).apply(block).navigation()
    } else {
        (context as? Activity)?.apply { ARouter.getInstance().build(url).apply(block).navigation(context, reqCode, callBack) }

        ARouter.getInstance().build(url).apply(block).navigation(context, callBack)
    }

}


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

/**---------------------------------RecyclerView begin --------------------------------**/

inline fun RecyclerView.grid(spanCount: Int, size: Int = 0, colorRes: Int = R.color.white) {
    layout(GridLayoutManager(context, spanCount), size, colorRes)
}

inline fun RecyclerView.linear(size: Int = dip(0.5f), colorRes: Int = R.color.white, orientation: Int = LinearLayoutManager.VERTICAL) {
    layout(LinearLayoutManager(context, orientation, false), size, colorRes)
}


inline fun RecyclerView.layout(layout: RecyclerView.LayoutManager, size: Int = 0, colorRes: Int = R.color.white) {
    layoutManager = layout
    addItemDecoration(HorizontalDivider.Builder(context).size(size).colorResId(colorRes).build())
    if (layout is GridLayoutManager || (layout as? LinearLayoutManager)?.orientation == LinearLayoutManager.HORIZONTAL) {
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


/**---------------------------------------RecyclerView end --------------------------------**/


/**---------------------------------------EditText begin --------------------------------**/
fun EditText.value() = text.toString()

fun EditText.trimValue() = value().trim()


/**---------------------------------------EditText end --------------------------------**/

/**---------------------------------------ImageView begin --------------------------------**/

fun ImageView.load(any: Any, requestOptions: RequestOptions.() -> Unit = {}) {
    GlideApp.with(this).load(any).apply(RequestOptions().apply(requestOptions)).into(this)
}
/**---------------------------------------ImageView end --------------------------------**/

/**---------------------------------------TextView begin --------------------------------**/

fun TextView.appendFontSpan(rSize: Float, char: CharSequence, begin: Int = 0, end: Int = char.length): TextView {
    SpannableString(char).apply {
        setSpan(RelativeSizeSpan(rSize), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        append(this)
    }
    return this
}

fun TextView.setFontSpan(rSize: Float, begin: Int = 0, end: Int = text.length): TextView {
    SpannableString(text).apply {
        setSpan(RelativeSizeSpan(rSize), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = this
    }
    return this
}

fun TextView.setColorSpan(color: Int, begin: Int = 0, end: Int = text.length): TextView {
    SpannableString(text).apply {
        setSpan(ForegroundColorSpan(color), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = this
    }
    return this
}

fun TextView.appendColorSpan(color: Int, char: CharSequence, begin: Int = 0, end: Int = char.length): TextView {
    SpannableString(char).apply {
        setSpan(ForegroundColorSpan(color), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        append(this)
    }
    return this
}

fun TextView.appendDrawable(drawable: Drawable, begin: Int = 0, end: Int = 1): TextView {
    SpannableString("1").apply {
        drawable.setBounds(0, 0, drawable.intrinsicWidth,
                drawable.intrinsicHeight)
        setSpan(VerticalImageSpan(drawable), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        append(this)
    }
    return this
}


/**---------------------------------------TextView end --------------------------------**/


/**---------------------------------------TextView begin --------------------------------**/

fun TabLayout.wrapLine() {
    post {
        //拿到tabLayout的mTabStrip属性
        val mTabStrip = getChildAt(0) as LinearLayout

        for (i in 0 until mTabStrip.childCount) {
            val tabView = mTabStrip.getChildAt(i)

            //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
            val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
            mTextViewField.isAccessible = true

            val mTextView = mTextViewField.get(tabView) as TextView

            tabView.setPadding(0, 0, 0, 0)

            var width = 0
            width = mTextView.width
            if (width == 0) {
                mTextView.measure(0, 0)
                width = mTextView.measuredWidth
            }

            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
            val params = tabView.layoutParams as LinearLayout.LayoutParams
            params.width = width
            params.leftMargin = dip(10)
            params.rightMargin = dip(10)
            tabView.layoutParams = params

            tabView.invalidate()
        }
    }
}


/**---------------------------------------TextView end --------------------------------**/