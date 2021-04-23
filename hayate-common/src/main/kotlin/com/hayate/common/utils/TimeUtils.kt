package com.hayate.common.utils


import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Flame on 2020/05/25.
 */

object TimeUtils {

    private val simpleDateFormat: SimpleDateFormat
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private fun getSimpleDateFormat(format: String): SimpleDateFormat {
        return SimpleDateFormat(format, Locale.getDefault())
    }

    fun getCurrentDate(format: String): String {
        return getSimpleDateFormat(format).format(Date())
    }

    fun getCurrentDate(formatPattern: SimpleDateFormat): String {
        return formatPattern.format(Date())
    }

    fun dateToString(date: Date?, format: String): String {
        return dateToString(date, getSimpleDateFormat(format))
    }

    fun dateToString(date: Date?, format: SimpleDateFormat = simpleDateFormat): String {
        return format.format(date)
    }

    fun stringToDate(dateString: String?, format: String): Date? {
        return stringToDate(dateString, getSimpleDateFormat(format))
    }

    fun stringToDate(dateString: String?, format: SimpleDateFormat = simpleDateFormat): Date? {
        var date: Date? = null
        try {
            date = format.parse(dateString)
        } catch(e: Exception) {
            e.printStackTrace()
        }
        return date
    }
}