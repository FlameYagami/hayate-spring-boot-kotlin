package com.hayate.auth.utils

import com.hayate.auth.constant.AuthConstant
import com.hayate.common.utils.AesUtils.decrypt
import com.hayate.common.utils.EncodeUtils
import org.slf4j.LoggerFactory

/**
 * @author Flame
 * @date 2020-04-17 16:24
 */

object TokenUtils {

    private val log = LoggerFactory.getLogger(this::class.java)

    // 从Token中获取用户ID
    fun fetchUserId(token: String): Long {
        return if(token.isEmpty()) {
            0
        } else try {
            val tokenBytes = EncodeUtils.hexStringToBytes(token)
            val clearTokenByte = decrypt(tokenBytes, AuthConstant.AUTH_AES_KEY, AuthConstant.AUTH_AES_IV)
            val clearToken = String(clearTokenByte)
            val tokenArray = clearToken.split(AuthConstant.TOKEN_SEPARATOR).toTypedArray()
            if(tokenArray.size < 3) {
                return 0
            }
            tokenArray[1].toLong()
        } catch(e: Exception) {
            log.error("Token decrypt error: " + e.localizedMessage)
            e.printStackTrace()
            0
        }
    }
}