package com.hayate.common.utils

import java.util.*

/**
 * Created by Flame on 2020/06/01.
 */
object EncodeUtils {

    /**
     * 将二进制转换成16进制
     */
    fun bytesToHexString(bytes: ByteArray): String {
        val stringBuilder = StringBuilder()
        for(b in bytes) {
            var hex = Integer.toHexString(b.toInt() and 0xFF)
            if(1 == hex.length) {
                hex = "0$hex"
            }
            stringBuilder.append(hex.toUpperCase())
        }
        return stringBuilder.toString()
    }

    fun hexStringToBytes(hexStr: String): ByteArray {
        if(hexStr.isEmpty()) {
            return byteArrayOf()
        }
        val result = ByteArray(hexStr.length / 2)
        for(i in 0 until hexStr.length / 2) {
            val high = hexStr.substring(i * 2, i * 2 + 1).toInt(16)
            val low = hexStr.substring(i * 2 + 1, i * 2 + 2).toInt(16)
            result[i] =(high * 16 + low).toByte()
        }
        return result
    }

    fun byteToInt(bytes: ByteArray?): Int {
        return String(bytes!!).toInt()
    }

    fun byteToInt(bytes: ByteArray?, radix: Int?): Int {
        return String(bytes!!).toInt(radix!!)
    }

    fun mergeBytes(byte1: ByteArray, byte2: ByteArray): ByteArray {
        val byte3 = ByteArray(byte1.size + byte2.size)
        System.arraycopy(byte1, 0, byte3, 0, byte1.size)
        System.arraycopy(byte2, 0, byte3, byte1.size, byte2.size)
        return byte3
    }

    /**
     * 生成随机数字和字母组合
     */
    fun getCharAndNum(length: Int): String {
        val stringBuilder = StringBuilder()
        val random = Random()
        for(i in 0 until length) {
            if(random.nextInt(2) % 2 == 0) { // 字符串
                val choice = if(random.nextInt(2) % 2 == 0) 65 else 97
                stringBuilder.append((choice + random.nextInt(26)).toChar())
            } else { // 数字
                stringBuilder.append(random.nextInt(10))
            }
        }
        return stringBuilder.toString()
    }
}