package com.zerdaket.iconadapter.internal.outline

import android.graphics.Path
import android.graphics.RectF
import com.zerdaket.iconadapter.Outline

/**
 * @author zerdaket
 * @date 2021/12/4 11:52 下午
 */
class RectangleOutline(
    private val topStartRadius: Float,
    private val topEndRadius: Float,
    private val bottomStartRadius: Float,
    private val bottomEndRadius: Float
) : Outline {

    @JvmOverloads
    constructor(radius: Float = 0f): this(radius, radius, radius, radius)

    override fun draw(path: Path, w: Float, h: Float, strokeWidth: Float) {
        val halfStrokeWidth = strokeWidth / 2f
        val rectF = RectF(halfStrokeWidth, halfStrokeWidth, w - halfStrokeWidth, h - halfStrokeWidth)
        val radii = floatArrayOf(
            topStartRadius, topStartRadius,
            topEndRadius, topEndRadius,
            bottomEndRadius, bottomEndRadius,
            bottomStartRadius, bottomStartRadius
        )
        path.addRoundRect(rectF, radii, Path.Direction.CW)
    }

}