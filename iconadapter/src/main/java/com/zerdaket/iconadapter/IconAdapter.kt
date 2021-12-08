package com.zerdaket.iconadapter

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.zerdaket.iconadapter.internal.RealIcon
import com.zerdaket.iconadapter.internal.outline.RectangleOutline

/**
 * @author zerdaket
 * @date 2021/10/25 12:47 上午
 */
class IconAdapter private constructor(builder: Builder) {

    val factor = builder.factor
    val nonRectangleFactor = builder.nonRectangleFactor
    val backgroundColor = builder.backgroundColor
    val strokeWidth = builder.strokeWidth
    val strokeColor = builder.strokeColor
    val outline: Outline = builder.outline

    fun newIcon(src: Bitmap): AdaptiveIcon = RealIcon(this, src)

    fun newIcon(src: Drawable): AdaptiveIcon = RealIcon(this, src)

    class Builder {

        internal var factor = 1f
        internal var nonRectangleFactor = 0.8f
        internal var backgroundColor = Color.TRANSPARENT
        internal var strokeWidth = 0f
        internal var strokeColor = Color.BLACK
        internal var outline: Outline = RectangleOutline()

        fun scale(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply {
            factor = value
        }

        fun nonRectangleScale(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply {
            nonRectangleFactor = value
        }

        fun backgroundColor(@ColorInt value: Int) = apply {
            backgroundColor = value
        }

        fun strokeWidth(value: Float) = apply {
            strokeWidth = value
        }

        fun strokeColor(@ColorInt value: Int) = apply {
            strokeColor = value
        }

        fun outline(value: Outline) = apply {
            outline = value
        }

        fun build() = IconAdapter(this)

    }

}