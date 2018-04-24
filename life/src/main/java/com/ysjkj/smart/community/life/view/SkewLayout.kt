package com.ysjkj.smart.community.life.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.LinearLayout
import org.jetbrains.anko.dip

/**
 * 倾斜的layout
 * @author yangqiang
 * @create 2018-04-24 上午11:48
 **/
class SkewLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var skewSize = dip(10).toFloat()
    var paintWidth = dip(1).toFloat()
    var paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        isAntiAlias=true
        style = Paint.Style.STROKE
        strokeWidth = paintWidth
        strokeCap = Paint.Cap.ROUND
        setShadowLayer(paintWidth*2,0f,0f,Color.GRAY)
    }
    var path = Path()

    init {

        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.apply {
            reset()
            moveTo(skewSize + paintWidth / 2, paintWidth / 2)
            lineTo(width.toFloat() - paintWidth / 2, paintWidth / 2)
            lineTo(width.toFloat() - skewSize - paintWidth / 2, height.toFloat() - paintWidth / 2)
            lineTo(0f + paintWidth / 2, height.toFloat() - paintWidth / 2)
            lineTo(skewSize + paintWidth / 2, paintWidth / 2)
        }
        canvas?.apply {
            drawPath(path, paint)
        }

    }
}