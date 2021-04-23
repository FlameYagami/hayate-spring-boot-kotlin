package com.hayate.app.utils

/**
 * @author Flame
 * @date 2020-10-23 10:21
 */

object CommonUtils {
    fun versionCompare(oldVersion: String, newVersion: String): Boolean {
        return if (oldVersion.isEmpty()) false
        else oldVersion < newVersion
    }
}