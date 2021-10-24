package com.zerdaket.iconadapter

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import kotlin.math.*

/**
 * @author zerdaket
 * @date 2021/10/25 12:47 上午
 */
class IconAdapter {

    private val alphaThreshold = 100

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
        var keepGoing = true
        run {
            var i = 0
            while (i < height && keepGoing) {
                var j = 0
                while (j < width && keepGoing) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        top = i
                        tlPoint[0] = j
                        tlPoint[1] = i
                        keepGoing = false
                    }
                    j++
                }
                i++
            }
        }

        // LT
        keepGoing = true
        run {
            var i = 0
            while (i < width && keepGoing) {
                var j = 0
                while (j < height && keepGoing) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        left = i
                        ltPoint[0] = i
                        ltPoint[1] = j
                        keepGoing = false
                    }
                    j++
                }
                i++
            }
        }

        // LB
        keepGoing = true
        run {
            var i = 0
            while (i < width && keepGoing) {
                var j = height - 1
                while (j >= 0 && keepGoing) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        left = min(left, i)
                        lbPoint[0] = i
                        lbPoint[1] = j
                        keepGoing = false
                    }
                    j--
                }
                i++
            }
        }

        // BL
        keepGoing = true
        run {
            var i = height - 1
            while (i >= 0 && keepGoing) {
                var j = 0
                while (j < width && keepGoing) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        bottom = i
                        blPoint[0] = j
                        blPoint[1] = i
                        keepGoing = false
                    }
                    j++
                }
                i--
            }
        }

        // BR
        keepGoing = true
        run {
            var i = height - 1
            while (i >= 0 && keepGoing) {
                var j = width - 1
                while (j >= 0 && keepGoing) {
                    alpha = Color.alpha(pixels[width * i + j])
                    if (alpha > alphaThreshold) {
                        bottom = max(bottom, i)
                        brPoint[0] = j
                        brPoint[1] = i
                        keepGoing = false
                    }
                    j--
                }
                i--
            }
        }

        // RB
        keepGoing = true
        run {
            var i = width - 1
            while (i >= 0 && keepGoing) {
                var j = height - 1
                while (j >= 0 && keepGoing) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        right = i
                        rbPoint[0] = i
                        rbPoint[1] = j
                        keepGoing = false
                    }
                    j--
                }
                i--
            }
        }

        // RT
        keepGoing = true
        run {
            var i = width - 1
            while (i >= 0 && keepGoing) {
                var j = 0
                while (j < height && keepGoing) {
                    alpha = Color.alpha(pixels[width * j + i])
                    if (alpha > alphaThreshold) {
                        right = max(right, i)
                        rtPoint[0] = i
                        rtPoint[1] = j
                        keepGoing = false
                    }
                    j++
                }
                i--
            }
        }

        // TR
        keepGoing = true
        var i = 0
        while (i < height && keepGoing) {
            var j = width - 1
            while (j >= 0 && keepGoing) {
                alpha = Color.alpha(pixels[width * i + j])
                if (alpha > alphaThreshold) {
                    top = min(top, i)
                    trPoint[0] = j
                    trPoint[1] = i
                    keepGoing = false
                }
                j--
            }
            i++
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
            rect[left, top, right] = bottom
            needScale = false
        } else {
            rect[0, 0, width] = height
            needScale = true
        }
        return needScale
    }

    private fun calculateDistanceOfTwoPoints(pointA: IntArray, pointB: IntArray): Int {
        val xDistance = abs(pointA[0] - pointB[0])
        val yDistance = abs(pointA[1] - pointB[1])
        return sqrt(xDistance.toDouble().pow(2.0) + yDistance.toDouble().pow(2.0)).toInt()
    }

}