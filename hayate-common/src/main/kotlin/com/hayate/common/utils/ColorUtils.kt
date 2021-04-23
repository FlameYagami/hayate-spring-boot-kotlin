package com.hayate.common.utils

import java.awt.Color

/**
 * Created by Flame on 2020/09/09.
 */
object ColorUtils {
    /**
     * Color的rgb字符串转Color的Int字符串
     * rgb -- "255255255"
     * return colorInt -- "-12590395"
     */
    fun rgbToInt(rgb: String): Int {
        val red = rgb.substring(0, 3).toInt()
        val green = rgb.substring(3, 6).toInt()
        val blue = rgb.substring(6, 9).toInt()
        return -0x1000000 or(red shl 16) or(green shl 8) or blue
    }

    /**
     * Color的Int转Color的rgb字符串
     * color - -12590395
     * return rgb -- "255255255"
     */
    fun intToRgb(colorInt: Int): String {
        val red = String.format("%03d", colorInt and 0xff0000 shr 16)
        val green = String.format("%03d", colorInt and 0x00ff00 shr 8)
        val blue = String.format("%03d", colorInt and 0x0000ff)
        return red + green + blue
    }

    fun RGBtoHSB(color: String): FloatArray {
        val r = color.substring(0, 3).toInt()
        val g = color.substring(3, 6).toInt()
        val b = color.substring(6, 9).toInt()
        val hsbArray = Color.RGBtoHSB(r, g, b, null)
        hsbArray[0] = hsbArray[0] * 360 // hue的值区间为0-1, 所以需要乘以360
        return hsbArray
    }

    fun HSBtoRGB(h: Float, s: Float, v: Float): String {
        var r = 0f
        var g = 0f
        var b = 0f
        val i =(h / 60 % 6).toInt()
        val f = h / 60 - i
        val p = v *(1 - s)
        val q = v *(1 - f * s)
        val t = v *(1 -(1 - f) * s)
        when(i) {
            0 -> {
                r = v
                g = t
                b = p
            }
            1 -> {
                r = q
                g = v
                b = p
            }
            2 -> {
                r = p
                g = v
                b = t
            }
            3 -> {
                r = p
                g = q
                b = v
            }
            4 -> {
                r = t
                g = p
                b = v
            }
            5 -> {
                r = v
                g = p
                b = q
            }
            else -> {
            }
        }
        return String.format("%03d%03d%03d",(r * 255.0).toInt(),(g * 255.0).toInt(),(b * 255.0).toInt())
    }
}