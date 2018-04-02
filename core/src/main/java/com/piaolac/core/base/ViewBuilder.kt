package com.piaolac.core.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.piaolac.brick.common.view.MultiStateView
import com.piaolac.core.R
import com.piaolac.core.ext.allMathParams
import com.piaolac.core.ext.matchParent
import com.piaolac.core.ext.statusBarHeight
import com.piaolac.core.ext.wrapContent
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.trello.rxlifecycle2.LifecycleProvider


class ViewBuilder(var ctx: Context) {
    private var contentResId = 0
    var lazyLoad = false
    var toolbar: Toolbar? = null
    var multiStateView: MultiStateView? = null
    var smartRefreshLayout: SmartRefreshLayout? = null

    fun withContent(resId: Int, lazyLoad: Boolean = false): ViewBuilder {
        this.contentResId = resId
        this.lazyLoad = lazyLoad
        return this
    }

    fun withToolbar(title: String = "", showBack: Boolean = false, backIconRes: Int = R.drawable.core_toolbar_back, menuId: Int = 0, toolbar: Toolbar.() -> Unit = {}): ViewBuilder {
        this.toolbar = Toolbar(ContextThemeWrapper(
                ctx,
                R.style.Toolbar_Theme
        )).apply {
            popupTheme = R.style.Toolbar_PopupTheme
            this.title = title
            if (menuId != 0) {
                inflateMenu(menuId)
            }
            toolbar.invoke(this)
            if (showBack) {
                navigationIcon = resources.getDrawable(backIconRes)
                (ctx as? AppCompatActivity)?.apply {
                    setNavigationOnClickListener {
                        onBackPressed()
                    }
                }
            }
        }
        return this
    }

    fun withState(targetId: Int = 0, multiStateView: MultiStateView.() -> Unit = {}): ViewBuilder {
        this.multiStateView = MultiStateView(ctx).apply {
            setTag(R.id.common_target_id, targetId)
            id = R.id.common_multi_state_view
            setViewForState(R.layout.view_common_loading, com.piaolac.brick.common.view.MultiStateView.LOADING)
            setViewForState(R.layout.view_common_error, com.piaolac.brick.common.view.MultiStateView.ERROR)
            setViewForState(R.layout.view_common_empty, com.piaolac.brick.common.view.MultiStateView.EMPTY)
            multiStateView.invoke(this)
        }
        return this
    }

    fun withRefresh(targetId: Int = 0, smartRefreshLayout: SmartRefreshLayout.() -> Unit = {}): ViewBuilder {
        this.smartRefreshLayout = SmartRefreshLayout(ctx).apply {
            setTag(R.id.common_target_id, targetId)
            id = R.id.common_refresh_view
            setRefreshHeader(MaterialHeader(ctx))
            setRefreshFooter(ClassicsFooter(ctx))
            isEnableOverScrollBounce = false
            setDisableContentWhenRefresh(true)
            setEnableHeaderTranslationContent(false)
            setEnableFooterTranslationContent(true)
            setDisableContentWhenLoading(true)
            isEnableAutoLoadMore = false
            isEnableLoadMore = false
            smartRefreshLayout.invoke(this)
        }
        return this
    }


    fun createContent(): View {
        var contentView: ViewGroup? = null
        if (contentResId != 0) {
            contentView = LayoutInflater.from(ctx).inflate(contentResId, null) as? ViewGroup
        }

        //根节点
        return LinearLayout(ctx).apply root@{
            orientation = LinearLayout.VERTICAL
            toolbar?.apply {
                var toolBarHegith = resources.getDimensionPixelOffset(R.dimen.toolbar_height)
                val typeValue = TypedValue()
                if (context.theme.resolveAttribute(android.R.attr.actionBarSize, typeValue, true)) {
                    toolBarHegith = TypedValue.complexToDimensionPixelSize(typeValue.data, context.resources.displayMetrics)
                }
                val stateHeight = context.statusBarHeight()
                id = R.id.common_toolbar_view
                popupTheme = R.style.Toolbar_PopupTheme
                setPadding(paddingLeft, stateHeight, paddingRight, paddingBottom)
                setContentInsetsAbsolute(0, 0)
                setContentInsetsRelative(0, 0)

                if (menu?.javaClass?.simpleName.equals("MenuBuilder")) {
                    try {
                        val method = menu.javaClass.getDeclaredMethod("setOptionalIconsVisible", java.lang.Boolean.TYPE)
                        method.isAccessible = true
                        method.invoke(menu, true)
                    } catch (e: Exception) {
                    }
                }
                (ctx as? Toolbar.OnMenuItemClickListener)?.apply {
                    setOnMenuItemClickListener(this)
                }
                TextView(ContextThemeWrapper(context, R.style.Toolbar_Title)).apply {
                    id = R.id.common_toolbar_title_view
                    text = title
                    addView(this, Toolbar.LayoutParams(wrapContent, wrapContent, Gravity.CENTER))
                }
                title = ""
                this@root.addView(this, ViewGroup.LayoutParams(matchParent, toolBarHegith + stateHeight))
            }

            contentView?.let {
//                this.viewTreeObserver.addOnGlobalLayoutListener {
//                    val rect = Rect()
//                    this.getWindowVisibleDisplayFrame(rect)
//                    val mainInvisibleHeight = this.rootView.height - rect.bottom
//                    val screenHeight = this.rootView.height//屏幕高度
//                    if (mainInvisibleHeight > screenHeight / 4) {
//                        val location = IntArray(2)
//                        it.getLocationInWindow(location)
//                        // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
//                        val srollHeight = location[1] + it.height - rect.bottom
//                        //5､让界面整体上移键盘的高度
//                        it.scrollTo(0, srollHeight)
//                    } else {
//                        //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
//                        it.scrollTo(0, 0)
//                    }
//                }
                this@root.addView(it, allMathParams)
            }

            //添加stateView
            multiStateView?.apply {
                var targetId: Int = getTag(R.id.common_target_id).toString().toInt()
                (if (targetId == 0) contentView else rootView.findViewById(targetId))?.let {
                    (it.parent as ViewGroup).apply {
                        indexOfChild(it).apply {
                            removeView(it)
                            addView(multiStateView.apply {
                                setViewForState(it, com.piaolac.brick.common.view.MultiStateView.CONTENT)
                            }, this, it.layoutParams)
                        }
                    }
                }

            }

            smartRefreshLayout?.let { ref ->
                var targetId: Int = ref.getTag(R.id.common_target_id).toString().toInt()
                (if (targetId == 0) contentView else rootView.findViewById(targetId))?.let { target ->
                    (target.parent as? ViewGroup)?.let { tp ->
                        tp.indexOfChild(target).apply {
                            tp.removeView(target)
                            ref.addView(target, tp.layoutParams)
                            if (tp is MultiStateView) {
                                tp.setViewForState(ref, MultiStateView.CONTENT)
                                tp.setState(MultiStateView.CONTENT)
                            } else {
                                tp.addView(ref, tp.layoutParams)
                            }
                        }
                    }
                }
            }
        }

    }

}


inline fun LifecycleProvider<*>.toolBar(): Toolbar? {
    return (this as? BaseActivity<*>)?.builder?.toolbar
            ?: (this as? BaseFragment<*>)?.builder?.toolbar
}


inline fun LifecycleProvider<*>.stateView(): MultiStateView? {
    return (this as? BaseActivity<*>)?.builder?.multiStateView
            ?: (this as? BaseFragment<*>)?.builder?.multiStateView
}


inline fun LifecycleProvider<*>.refreshView(): SmartRefreshLayout? {
    return (this as? BaseActivity<*>)?.builder?.smartRefreshLayout
            ?: (this as? BaseFragment<*>)?.builder?.smartRefreshLayout
}

