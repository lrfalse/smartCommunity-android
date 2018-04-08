package com.ysjkj.smart.community.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.ysjkj.smart.community.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by yangqiang on 2018/4/3.
 */
class ArcMenuView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    var arcRadius = 0f
    var centerButton: ImageView? = null
    var subMenuIsShow = false
    var marginBottom = 0
    var arcMenuOffset = 0
    var menuChangeListener: (Boolean, Boolean) -> Unit = { centerIsOpen, subIsShow -> }
    var menuClickListener: Int.() -> Unit = {}
    var maskColor = 0
    var centerMenuIsOpen = false

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ArcMenuView).apply {
            marginBottom = getDimensionPixelOffset(R.styleable.ArcMenuView_arc_margin_bottom, 0)
            arcRadius = getDimension(R.styleable.ArcMenuView_arc_radius, 0f)
            maskColor = getColor(R.styleable.ArcMenuView_arc_mask_color, Color.TRANSPARENT)
            recycle()
        }
        backgroundColor = Color.TRANSPARENT
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (centerMenuIsOpen) {
            close()
            return true
        }
        return super.onTouchEvent(event)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        centerButton = findViewById(R.id.arc_menu_center_botton)
        centerButton?.apply {
            arcMenuOffset = measuredWidth
        }
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed) {
            centerButton?.let {
                var left = measuredWidth / 2 - it.measuredWidth / 2
                var top = measuredHeight - it.measuredHeight - marginBottom
                (0 until childCount).forEach { fe ->
                    if (getChildAt(fe) != centerButton) {
                        getChildAt(fe).apply {
                            layout(left + it.measuredWidth - measuredWidth, top + it.measuredHeight - measuredHeight, left + measuredWidth, top + measuredHeight)
                            onClick {
                                menuClickListener.invoke(indexOfChild(this@apply))
                                hideSubMenu()
                            }
                        }
                    }
                }
                it.layout(left, top, left + it.measuredWidth, top + it.measuredHeight)
                it.onClick {

                    if (isOpen()) {
                        close()
                    } else {
                        open()
                    }
                }
                it.bringToFront()
            }
        }
    }


    /**
     * 计算menu的位置
     */
    private fun computeMenuLayout(view: View, radius: Float) {
        var index = indexOfChild(view)
        var cl = (radius) * Math.cos(Math.PI / (childCount - 2) * (index))
        var ct = (radius) * Math.sin(Math.PI / (childCount - 2) * (index))
        cl = this.measuredWidth / 2 - cl - view.measuredWidth / 2
        ct = this.measuredHeight - ct - view.measuredHeight - marginBottom
        centerButton?.let {
            ct -= radius / arcRadius * ((it.measuredHeight / 4 * 3))
            view.layout(cl.toInt(), ct.toInt(), cl.toInt() + view.measuredWidth, ct.toInt() + view.measuredHeight)
        }
    }

    /**
     * 显示菜单
     */
    private fun show() {
        post {
            backgroundColor = maskColor
            (0 until childCount - 1).forEachIndexed { index, i ->
                getChildAt(index).clearAnimation()
                AnimationSet(false).apply {
                    RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).let {
                        it.duration = 200
                        it.startOffset = (index * 40).toLong()
                        it.fillAfter = true
                        it.interpolator = LinearInterpolator()
                        addAnimation(it)
                    }
                    getChildAt(index).animation = this
                }
                ValueAnimator.ofFloat(0f, arcRadius).apply {
                    duration = 150
                    startDelay = (index * 60).toLong()
                    interpolator = LinearInterpolator()
                    addUpdateListener {
                        computeMenuLayout(getChildAt(index), it.animatedValue.toString().toFloat())
                    }
                }.start()
            }
            subMenuIsShow = true
            centerMenuIsOpen = true
            centerButton?.setImageResource(R.drawable.main_tabs_key_btn_close)
            menuChangeListener.invoke(centerMenuIsOpen, subMenuIsShow)
        }
    }

    /**
     * 隐藏菜单
     */
    private fun hide() {
        post {
            backgroundColor = Color.TRANSPARENT
            (0 until childCount - 1).forEachIndexed { index, i ->
                getChildAt(index).clearAnimation()
                AnimationSet(false).apply {
                    RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).let {
                        it.duration = 200
                        it.startOffset = (index * 40).toLong()
                        it.fillAfter = true
                        it.interpolator = LinearInterpolator()
                        addAnimation(it)
                    }
                    getChildAt(index).animation = this
                }
                ValueAnimator.ofFloat(arcRadius, 0f).apply {
                    duration = 150
                    startDelay = (index * 60).toLong()
                    interpolator = LinearInterpolator()
                    addUpdateListener {
                        computeMenuLayout(getChildAt(index), it.animatedValue.toString().toFloat())
                    }
                }.start()
            }
            subMenuIsShow = false
            centerMenuIsOpen = false
            centerButton?.setImageResource(R.drawable.main_tabs_key_btn_open)
            menuChangeListener.invoke(centerMenuIsOpen, subMenuIsShow)
        }
    }

    /**
     * 关闭子菜单
     */
    fun hideSubMenu() {
        post {
            (0 until childCount - 1).forEachIndexed { index, i ->
                getChildAt(index).clearAnimation()
                AnimationSet(false).apply {
                    RotateAnimation(360f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).let {
                        it.duration = 200
                        it.startOffset = (index * 40).toLong()
                        it.fillAfter = true
                        it.interpolator = LinearInterpolator()
                        addAnimation(it)
                    }
                    getChildAt(index).animation = this
                }
                ValueAnimator.ofFloat(arcRadius, 0f).apply {
                    duration = 150
                    startDelay = (index * 60).toLong()
                    interpolator = LinearInterpolator()
                    addUpdateListener {
                        computeMenuLayout(getChildAt(index), it.animatedValue.toString().toFloat())
                    }
                }.start()
            }
            subMenuIsShow = false
            centerMenuIsOpen = true
            menuChangeListener.invoke(centerMenuIsOpen, subMenuIsShow)
        }
    }

    /**
     * 关闭菜单
     */
    fun close() {
        //主菜单打开，子菜单关闭
        if (centerMenuIsOpen && !subMenuIsShow) {
            centerMenuIsOpen = false
            centerButton?.setImageResource(R.drawable.main_tabs_key_btn_open)
            backgroundColor = Color.TRANSPARENT
            menuChangeListener.invoke(centerMenuIsOpen, subMenuIsShow)
        } else {
            hide()
        }
    }

    /**
     * 打开菜单
     */
    fun open() {
        show()
    }

    fun isOpen() = centerMenuIsOpen

    fun onMenuChangeListener(block: (centerIsOpen: Boolean, subIsShow: Boolean) -> Unit) {
        menuChangeListener = block
    }

    fun onMenuClickListener(block: Int.() -> Unit) {
        menuClickListener = block
    }
}