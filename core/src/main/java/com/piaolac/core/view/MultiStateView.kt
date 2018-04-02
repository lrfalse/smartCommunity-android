package com.piaolac.brick.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.piaolac.core.R

/**
 * Created by YangQiang on 2017/9/2.
 */

class MultiStateView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var contentView: View? = null
    var loadingView: View? = null
    var errorView: View? = null
    var emptyView: View? = null
    var viewState: Int = 0
    var transparentLoading: Boolean = false
    var inflater: LayoutInflater = LayoutInflater.from(context)


    companion object {
        val UNKNOWN = -1
        val CONTENT = 0
        val ERROR = 1
        val EMPTY = 2
        val LOADING = 3
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView)
        attrs?.let {

            val loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1)
            if (loadingViewResId > -1) {
                loadingView = inflater.inflate(loadingViewResId, this, false)?.apply {
                    addView(this, layoutParams)
                }
            }

            val emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1)
            if (emptyViewResId > -1) {
                this.emptyView = inflater.inflate(emptyViewResId, this, false)?.apply {
                    addView(this, layoutParams)
                }

            }

            val errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1)
            if (errorViewResId > -1) {
                this.errorView = inflater.inflate(errorViewResId, this, false)?.apply {
                    addView(this, layoutParams)
                }
            }
            viewState = a.getInt(R.styleable.MultiStateView_msv_viewState, -1)
            a.recycle()
        }
    }


    fun setState(state: Int) {
        viewState = state
        when (state) {
            ERROR -> {

                emptyView?.visibility = View.GONE
                loadingView?.visibility = View.GONE
                contentView?.visibility = View.GONE
                errorView?.apply { bringToFront() }?.visibility = View.VISIBLE
            }
            EMPTY -> {
                errorView?.visibility = View.GONE
                loadingView?.visibility = View.GONE
                contentView?.visibility = View.GONE
                emptyView?.apply { bringToFront() }?.visibility = View.VISIBLE
            }
            LOADING -> {
                errorView?.visibility = View.GONE
                emptyView?.visibility = View.GONE
                contentView?.visibility = if (transparentLoading) View.VISIBLE else View.GONE
                loadingView?.apply { bringToFront() }?.visibility = View.VISIBLE
            }
            else -> {
                emptyView?.visibility = View.GONE
                loadingView?.visibility = View.GONE
                errorView?.visibility = View.GONE
                contentView?.apply { bringToFront() }?.visibility = View.VISIBLE
            }
        }
    }

    fun setViewForState(resId: Int, state: Int) {
        setViewForState(inflater.inflate(resId, this, false), state)
    }


    fun setViewForState(view: View, state: Int) {
        when (state) {
            ERROR -> {
                errorView?.apply {
                    removeView(this)
                }
                errorView = view.apply { addView(this) }
            }
            EMPTY -> {
                emptyView?.apply {
                    removeView(this)
                }
                emptyView = view.apply { addView(this) }
            }
            LOADING -> {
                loadingView?.apply {
                    removeView(this)
                }
                loadingView = view.apply { addView(this) }
            }
            else -> {
                contentView?.apply {
                    removeView(this)
                }
                contentView = view.apply { addView(this) }
            }
        }

        setState(state)
    }
}