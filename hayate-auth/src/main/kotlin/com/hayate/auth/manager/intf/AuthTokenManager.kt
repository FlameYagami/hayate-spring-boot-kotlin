package com.hayate.auth.manager.intf

/**
 * @author Flame
 * @date 2020-03-12 11:06
 */

interface AuthTokenManager {
    fun createToken(userId: Long): String
    fun checkToken(token: String): Boolean
    fun deleteToken(userId: Long)
}