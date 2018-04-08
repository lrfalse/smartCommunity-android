package com.ysjkj.smart.community.door.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ysjkj.smart.community.door.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import java.util.*

/**
 * 远程开门钥匙圈
 * @author yangqiang
 * @create 2018-04-08 下午4:10
 **/
class KeyCircleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var bgColor = resources.getColor(R.color.door_shake_circle_bg_color)
    val colors = mutableListOf(Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN, Color.DKGRAY, Color.GREEN, Color.GRAY, Color.LTGRAY, Color.MAGENTA)
    var circleStrokeWidth = dip(1).toFloat()
    var circleCount = 3
    var keyCount = Random().nextInt(8) + 2
    var minRadius = 0
    var keyRadius = 0
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = circleStrokeWidth
    }
    var keyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        alpha = 200
        textSize = dip(15).toFloat()
        isFakeBoldText = true
    }

    var keyDatas = mutableListOf<KeyData>()

    init {

        context.obtainStyledAttributes(attrs, R.styleable.KeyCircleView).apply {
            keyRadius = getDimensionPixelOffset(R.styleable.KeyCircleView_key_radius, 0)
            recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        minRadius = radius() / 5 * 3
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCircle(canvas)
        drawKey(canvas)
    }

    private fun radius() = measuredWidth / 2 - keyRadius

    /**
     * 画背景圆圈
     */
    private fun drawCircle(canvas: Canvas?) {
        var levelRadius = (radius() - minRadius) / (circleCount - 1)
        (0 until circleCount).forEachIndexed { index, i ->
            var radius = minRadius + levelRadius * i - circleStrokeWidth / 2f
            paint.color = bgColor
            canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, paint)
        }
    }


    /**
     * 画钥匙
     */
    private fun drawKey(canvas: Canvas?) {
        if (keyCount < 1) {
            return
        }
        keyDatas.clear()
        keyPaint.color = colors[0]
        keyPaint.alpha = 150
        var cx = measuredWidth / 2f
        var cy = measuredHeight / 2f
        var radius = minRadius + (radius() - minRadius) / (circleCount - 1) * (circleCount - 1) - circleStrokeWidth / 2f

        //画实心圆
        canvas?.drawCircle(measuredWidth / 2f, measuredHeight / 2f, keyRadius.toFloat(), keyPaint)

        //画文字
        drawText(canvas, "RC0", measuredWidth / 2f, measuredHeight / 2f, keyPaint)

        //将圆的路径保存起来
        saveKeyPath(measuredWidth / 2f, measuredHeight / 2f, "RC0")

        if (keyCount < 2) {
            return
        }
        var angeOffset = 360 / (keyCount - 1)
        var startOffset = if (keyCount % 2 == 0) -90 else 0
        (0 until keyCount - 1).forEachIndexed { index, i ->
            keyPaint.color = colors[(index + 1) % colors.size]
            keyPaint.alpha = 150

            var left = cx + radius * Math.cos((startOffset + angeOffset * index) * Math.PI / 180)
            var top = cy + radius * Math.sin((startOffset + angeOffset * index) * Math.PI / 180)

            canvas?.drawCircle(left.toFloat(), top.toFloat(), keyRadius.toFloat(), keyPaint)

            drawText(canvas, "RC${index + 1}", left.toFloat(), top.toFloat(), keyPaint)

            saveKeyPath(left.toFloat(), top.toFloat(), "RC${index + 1}")

        }
    }

    /**
     * 画文字
     */
    private fun drawText(canvas: Canvas?, text: String, left: Float, top: Float, paint: Paint) {
        text.apply {
            paint.color = Color.BLACK
            paint.fontMetrics.let { fm ->
                paint.measureText(this).let {
                    canvas?.drawText(this, (left - it / 2), top + (fm.bottom - fm.top) / 2 - fm.bottom, keyPaint)
                }
            }
        }
    }

    /**
     * 添加钥匙路径
     */
    private fun saveKeyPath(left: Float, top: Float, key: String) {
        Path().apply {
            addCircle(left, top, keyRadius.toFloat(), Path.Direction.CCW)
            keyDatas.add(KeyData(this, key))
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        keyDatas.forEach {
            if (it.contains(event.x, event.y)) {
                context.toast("${it.key}")
            }
        }
        return super.onTouchEvent(event)
    }

    data class KeyData(val path: Path, val key: String) {

        /**
         * 判断用户点击是否在圆上
         */
        fun contains(x: Float, y: Float): Boolean {
            return RectF().let {
                path.computeBounds(it, true)
                Region().setPath(path, Region(it.left.toInt(), it.top.toInt(), it.right.toInt(), it.bottom.toInt()))
                it.contains(x, y)
            }

        }
    }
}