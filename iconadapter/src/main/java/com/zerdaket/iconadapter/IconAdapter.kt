package com.zerdaket.iconadapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.zerdaket.iconadapter.internal.RealIcon

/**
 * @author zerdaket
 * @date 2021/10/25 12:47 上午
 */
class IconAdapter private constructor(builder: Builder) {

    fun newIcon(src: Bitmap): AdaptiveIcon = RealIcon(this, src)

    fun newIcon(src: Drawable): AdaptiveIcon = RealIcon(this, src)

    class Builder {

        fun build() = IconAdapter(this)

    }

}