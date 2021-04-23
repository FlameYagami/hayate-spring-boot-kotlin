package com.hayate.common

import com.hayate.common.utils.TimeUtils.getDayOfWeek
import com.hayate.common.utils.TimeUtils.getTimeZoneDate
import com.hayate.common.utils.TimeUtils.stringToDate

/**
 * Created by Flame on 2020/06/19.
 */
object TimeUtilsTest {

    fun main(args: Array<String>) {
        println(getTimeZoneDate(""))
        println(getTimeZoneDate("America/Chicago"))
        val date = stringToDate("2020-06-23 10:00:00")
        println(getDayOfWeek(date, ""))
        println(getDayOfWeek(date, "America/Chicago"))
    }
}