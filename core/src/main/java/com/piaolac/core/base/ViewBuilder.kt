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
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip


class ViewBuilder(var ctx: Context) {
    private var contentResId = 0
    var lazyLoad = false
    var toolbar: Toolbar? = null
    var multiStateView: MultiStateView? = null
    var smartRefreshLayout: SmartRefreshLayout? = null
    var xmlIsRoot = false
    private var toolBarConfig: Toolbar.() -> Unit = {}
    private var multiStateConfig: MultiStateView.() -> Unit = {}
    private var smartRefreshConfig: SmartRefreshLayout.() -> Unit = {}
    private var rootLayout: ViewGroup? = null
    private var contentView: ViewGroup? = null
    fun withContent(resId: Int, lazyLoad: Boolean = false, isRoot: Boolean = false): ViewBuilder {
        this.contentResId = resId
        this.lazyLoad = lazyLoad
        this.xmlIsRoot = isRoot

        if (!xmlIsRoot) {
            rootLayout = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                if (contentResId != 0) {
                    (LayoutInflater.from(ctx).inflate(contentResId, null) as? ViewGroup)?.let {
                        contentView = it
                        this.addView(it, allMathParams)
                    }
                }
            }
        } else {
            if (contentResId != 0) {
                rootLayout = (LayoutInflater.from(ctx).inflate(contentResId, null) as? ViewGroup)
                contentView = rootLayout
            }
        }
        return this
    }

    fun withToolbar(title: String = "", showBack: Boolean = false, backIconRes: Int = R.drawable.core_toolbar_back, menuId: Int = 0, stytle: Int = R.style.Toolbar_Theme, unLine: Boolean = true, toolbar: Toolbar.() -> Unit = {}): ViewBuilder {
        if (xmlIsRoot) {
            rootLayout?.apply {
                findViewById<Toolbar>(R.id.common_toolbar_view)?.apply {
                    this@ViewBuilder.toolbar = this
                    initToolbar(null, menuId)
                }
            }

        } else {
            Toolbar(ContextThemeWrapper(
                    ctx,
                    stytle
            )).apply {
                popupTheme = R.style.Toolbar_PopupTheme
                this.title = title

                if (showBack) {
                    navigationIcon = resources.getDrawable(backIconRes)
                    (ctx as? AppCompatActivity)?.apply {
                        setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }
                }
                this@ViewBuilder.toolbar = this
                initToolbar(rootLayout, menuId)
                if (unLine) {
                    rootLayout?.addView(TextView(context).apply { backgroundColor = resources.getColor(R.color.toolbar_line_color) }, 1, ViewGroup.LayoutParams(matchParent, dip(1f)))
                }
            }

        }
        return this
    }

    fun withState(targetId: Int = 0, multiStateView: MultiStateView.() -> Unit = {}): ViewBuilder {
        if (xmlIsRoot) {

            rootLayout?.apply {
                findViewById<MultiStateView>(R.id.common_multi_state_view)?.apply {
                    this@ViewBuilder.multiStateView = this
                    initMultiStateView(null)
                }
            }


        } else {
            this.multiStateView = MultiStateView(ctx).apply {
                setTag(R.id.common_target_id, targetId)
                id = R.id.common_multi_state_view

            }
            initMultiStateView(contentView)
        }
        return this
    }

    fun withRefresh(targetId: Int = 0, smartRefreshLayout: SmartRefreshLayout.() -> Unit = {}): ViewBuilder {
        if (xmlIsRoot) {
            rootLayout?.apply {
                findViewById<SmartRefreshLayout>(R.id.common_refresh_view)?.apply {
                    this@ViewBuilder.smartRefreshLayout = this
                    initMultiStateView(null)
                }
            }
        } else {
            this.smartRefreshLayout = SmartRefreshLayout(ctx).apply {
                setTag(R.id.common_target_id, targetId)
                id = R.id.common_refresh_view
            }
            initSmartRefresh(contentView)
        }

        return this
    }


    fun createContent(): View {
        return rootLayout!!
    }

    private fun initMultiStateView(contentView: ViewGroup?) {
        multiStateView?.apply {
            setViewForState(R.layout.view_common_loading, com.piaolac.brick.common.view.MultiStateView.LOADING)
            setViewForState(R.layout.view_common_error, com.piaolac.brick.common.view.MultiStateView.ERROR)
            setViewForState(R.layout.view_common_empty, com.piaolac.brick.common.view.MultiStateView.EMPTY)

            multiStateConfig.invoke(this)
            contentView?.let { ct ->
                var targetId: Int = getTag(R.id.common_target_id).toString().toInt()
                (if (targetId == 0) contentView else contentView?.findViewById(targetId))?.let {
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
        }
    }

    private fun initSmartRefresh(root: ViewGroup?) {
        smartRefreshLayout?.apply {
            setRefreshHeader(MaterialHeader(ctx))
            setRefreshFooter(ClassicsFooter(ctx))
            isEnableOverScrollBounce = false
            setDisableContentWhenRefresh(true)
            setEnableHeaderTranslationContent(false)
            setEnableFooterTranslationContent(true)
            setDisableContentWhenLoading(true)
            isEnableAutoLoadMore = false
            isEnableLoadMore = false
            smartRefreshConfig.invoke(this)

        }

        smartRefreshLayout?.let { ref ->

            root?.let { ct ->
                var targetId: Int = ref.getTag(R.id.common_target_id).toString().toInt()
                (if (targetId == 0) root else root.findViewById(targetId))?.let { target ->
                    (target.parent as? ViewGroup)?.let { tp ->
                        tp.indexOfChild(target).apply {
                            tp.removeView(target)
                            ref.addView(target,this, target.layoutParams)
                            if (tp is MultiStateView) {
                                tp.setViewForState(ref, MultiStateView.CONTENT)
                                tp.setState(MultiStateView.CONTENT)
                            } else {
                                tp.addView(ref,this, target.layoutParams)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initToolbar(root: ViewGroup?, menuId: Int) {
        toolbar?.apply {
            var toolBarHegith = resources.getDimensionPixelOffset(R.dimen.toolbar_height)
            val typeValue = TypedValue()
            if (context.theme.resolveAttribute(android.R.attr.actionBarSize, typeValue, true)) {
                toolBarHegith = TypedValue.complexToDimensionPixelSize(typeValue.data, context.resources.displayMetrics)
            }
            var stateHeight = context.statusBarHeight()

            stateHeight = 0
            id = R.id.common_toolbar_view
            popupTheme = R.style.Toolbar_PopupTheme
            setPadding(paddingLeft, stateHeight, paddingRight, paddingBottom)
            setContentInsetsAbsolute(0, 0)
            setContentInsetsRelative(0, 0)

            if (menuId != 0) {
                inflateMenu(menuId)
            }
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
                setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.toolbar_text_size))
                addView(this, Toolbar.LayoutParams(wrapContent, wrapContent, Gravity.CENTER))
            }
            toolBarConfig.invoke(this)
            title = ""


            root?.addView(this, 0, ViewGroup.LayoutParams(matchParent, toolBarHegith + stateHeight))
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

