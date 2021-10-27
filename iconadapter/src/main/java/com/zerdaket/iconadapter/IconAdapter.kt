package com.zerdaket.iconadapter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import kotlin.math.*

/**
 * @author zerdaket
 * @date 2021/10/25 12:47 上午
 */
class IconAdapter {

    private val alphaThreshold = 100
    private val factor = 0.8f

    private fun calculateOutlineRect(bitmap: Bitmap, rect: Rect): Boolean {
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var alpha: Int
        val width = bitmap.width
        val height = bitmap.height
        var left = 0
        var right = width
        var top = 0
        var bottom = height
        val tlPoint = IntArray(2)
        val ltPoint = IntArray(2)
        val lbPoint = IntArray(2)
        val blPoint = IntArray(2)
        val brPoint = IntArray(2)
        val rbPoint = IntArray(2)
        val rtPoint = IntArray(2)
        val trPoint = IntArray(2)


        // TL
        run {
            for (i in 0 until height) {
                for (j in 0 until width) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        top = i
                        tlPoint[0] = j
                        tlPoint[1] = i
                        return@run
                    }
                }
            }
        }

        // LT
        run {
            for (i in 0 until width) {
                for (j in 0 until height) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        left = i
                        ltPoint[0] = i
                        ltPoint[1] = j
                        return@run
                    }
                }
            }
        }

        // LB
        run {
            for (i in 0 until width) {
                for (j in height - 1 downTo 0) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        left = min(left, i)
                        lbPoint[0] = i
                        lbPoint[1] = j
                        return@run
                    }
                }
            }
        }

        // BL
        run {
            for (i in height - 1 downTo 0) {
                for (j in 0 until width) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        bottom = i
                        blPoint[0] = j
                        blPoint[1] = i
                        return@run
                    }
                }
            }
        }

        // BR
        run {
            for (i in height - 1 downTo 0) {
                for (j in width - 1 downTo 0) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        bottom = max(bottom, i)
                        brPoint[0] = j
                        brPoint[1] = i
                        return@run
                    }
                }
            }
        }

        // RB
        run {
            for (i in width - 1 downTo 0) {
                for (j in height - 1 downTo 0) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        right = i
                        rbPoint[0] = i
                        rbPoint[1] = j
                        return@run
                    }
                }
            }
        }

        // RT
        run {
            for (i in width - 1 downTo 0) {
                for (j in 0 until height) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        right = max(right, i)
                        rtPoint[0] = i
                        rtPoint[1] = j
                        return@run
                    }
                }
            }
        }

        // TR
        run {
            for (i in 0 until height) {
                for (j in width - 1 downTo 0) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        top = min(top, i)
                        trPoint[0] = j
                        trPoint[1] = i
                        return@run
                    }
                }
            }
        }

        val lt2tl = calculateDistanceOfTwoPoints(ltPoint, tlPoint)
        val rt2tr = calculateDistanceOfTwoPoints(rtPoint, trPoint)
        val lb2bl = calculateDistanceOfTwoPoints(lbPoint, blPoint)
        val rb2br = calculateDistanceOfTwoPoints(rbPoint, brPoint)
        val side = max(width, height)
        val pointA = intArrayOf(0, side / 2)
        val pointB = intArrayOf(side / 2, 0)
        val distance = calculateDistanceOfTwoPoints(pointA, pointB)
        val standard = distance / 2
        val needScale: Boolean
        if (lt2tl < standard && rt2tr < standard && lb2bl < standard && rb2br < standard) {
            rect.set(left, top, right, bottom)
            needScale = false
        } else {
            rect.set(0, 0, width, height)
            needScale = true
        }
        return needScale
    }

    private fun calculateDistanceOfTwoPoints(pointA: IntArray, pointB: IntArray): Int {
        val xDistance = abs(pointA[0] - pointB[0])
        val yDistance = abs(pointA[1] - pointB[1])
        return sqrt(xDistance.toDouble().pow(2.0) + yDistance.toDouble().pow(2.0)).toInt()
    }

    private fun cropOutline(dst: Bitmap): Bitmap {
        val rect = Rect()
        val needScale = calculateOutlineRect(dst, rect)
        return if (needScale) {
            val width = dst.width
            val height = dst.height
            val scaleWidth = width * factor
            val scaleHeight = height * factor
            val bitmap = Bitmap.createBitmap(dst.width, dst.height, Bitmap.Config.ARGB_8888)
            val scaleBitmap = Bitmap.createScaledBitmap(bitmap, scaleWidth.toInt(), scaleHeight.toInt(), false)
            val dstX = (width - scaleWidth) / 2
            val dstY = (height - scaleHeight) / 2
            val canvas = Canvas(bitmap)
            canvas.drawBitmap(scaleBitmap, dstX, dstY, null)
            bitmap
        } else {
            Bitmap.createBitmap(dst, rect.left, rect.top, rect.width(), rect.height())
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            val width = drawable.intrinsicWidth.coerceAtLeast(1)
            val height = drawable.intrinsicHeight.coerceAtLeast(1)
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

}