package com.ysjkj.smart.community.provider.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle

/**
 * 下拉刷新头部
 * @author yangqiang
 * @create 2018-04-11 下午5:09
 **/
class FishRefreshHeader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), RefreshHeader {
    override fun getSpinnerStyle(): SpinnerStyle = SpinnerStyle.Translate

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {

        return 500
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout?, height: Int, extendHeight: Int) {
    }

    override fun onPulling(percent: Float, offset: Int, height: Int, extendHeight: Int) {
    }

    override fun getView() = this

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onReleasing(percent: Float, offset: Int, height: Int, extendHeight: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, extendHeight: Int) {
    }

    override fun onStateChanged(refreshLayout: RefreshLayout?, oldState: RefreshState?, newState: RefreshState?) {
    }

    override fun isSupportHorizontalDrag() = false


}