package com.ysjkj.smart.community.door.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.ysjkj.smart.community.door.R
import org.jetbrains.anko.*

/**
 * 摇一摇
 * @author
 * @create 2018-04-08 下午1:41
 **/
class ShakeCircleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    var arcCount = 3
    var bgColor = resources.getColor(R.color.door_shake_circle_bg_color)
    var arcColors = mutableListOf(resources.getColor(R.color.door_shake_arc_3_color), resources.getColor(R.color.door_shake_arc_2_color), resources.getColor(R.color.door_shake_arc_1_color))
    var sweepAnge = mutableListOf(30f, 60f, 225f)
    var angeOffset = 0f
    var minRadius = 0
    var circleStrokeWidth = dip(1).toFloat()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = circleStrokeWidth
    }
    var angeAnimator = ObjectAnimator.ofFloat(0f, 360f).apply {
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        duration = 1300
        addUpdateListener {
            angeOffset = it.animatedValue.toString().toFloat()
            postInvalidate()
        }
    }


    var centerIcon: ImageView = imageView {
        imageResource = R.drawable.door_shake_icon
        layoutParams = RelativeLayout.LayoutParams(wrapContent, wrapContent).apply {
            centerInParent()
        }
    }
    var rateAnimator = ObjectAnimator.ofFloat(centerIcon, "rotation", -10f, 10f).apply {
        interpolator = CycleInterpolator(5f)
        duration = 600
        startDelay = 1000
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                starRotationAnimation()
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }

    init {
        setWillNotDraw(false)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerIcon.measuredWidth
        minRadius = measuredWidth / 2 / 5 * 3
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawArc(canvas)
    }


    /**
     * 花圆环
     */
    private fun drawCircle(canvas: Canvas?) {
        var levelRadius = (measuredWidth / 2 - minRadius) / (arcCount - 1)
        (0 until arcCount).forEachIndexed { index, i ->
            var radius = minRadius + levelRadius * i - circleStrokeWidth / 2f
            paint.color = bgColor
            canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, paint)
        }
    }

    /**
     * 画圆弧
     */
    private fun drawArc(canvas: Canvas?) {
        var levelRadius = (measuredWidth / 2 - minRadius) / (arcCount - 1)
        (0 until arcCount).forEachIndexed { index, i ->
            var radius = minRadius + levelRadius * i - circleStrokeWidth / 2f
            RectF((measuredWidth / 2 - radius).toFloat(), (measuredHeight / 2 - radius), (measuredWidth / 2 + radius), (measuredHeight / 2 + radius)).apply {
                paint.color = arcColors[index]
                canvas?.drawArc(this, -90f + angeOffset, sweepAnge[index], false, paint)
            }
        }
    }


    private fun starRotationAnimation() {
        postDelayed({
            rateAnimator.start()
        }, 2000)
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        Log.d("xxx", "onViewRemoved")
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.d("xxx", "onVisibilityChanged $changedView")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        angeAnimator.cancel()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        angeAnimator.start()
        rateAnimator.start()
        Log.d("xxx", "onAttachedToWindow ")
    }
}