package com.zerdaket.iconadapter

import android.graphics.Path

/**
 * @author zerdaket
 * @date 2021/12/4 11:50 下午
 */
interface Outline {

    fun draw(path: Path, w: Float, h: Float, strokeWidth: Float)

}