package com.zerdaket.iconadapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.FloatRange
import com.zerdaket.iconadapter.internal.RealIcon

/**
 * @author zerdaket
 * @date 2021/10/25 12:47 上午
 */
class IconAdapter private constructor(builder: Builder) {

    val factor = builder.factor
    val nonRectangleFactor = builder.nonRectangleFactor

    fun newIcon(src: Bitmap): AdaptiveIcon = RealIcon(this, src)

    fun newIcon(src: Drawable): AdaptiveIcon = RealIcon(this, src)

    class Builder {

        var factor = 1f
        var nonRectangleFactor = 0.8f

        fun scale(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply {
            factor = value
        }

        fun nonRectangleScale(@FloatRange(from = 0.0, to = 1.0) value: Float) = apply {
            nonRectangleFactor = value
        }

        fun build() = IconAdapter(this)

    }

}