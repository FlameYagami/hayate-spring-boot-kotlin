package com.hayate.auth.manager.impl

import com.hayate.auth.constant.AuthConstant
import com.hayate.auth.manager.intf.AuthTokenManager
import com.hayate.auth.utils.TokenUtils
import com.hayate.common.utils.AesUtils.encrypt
import com.hayate.common.utils.EncodeUtils
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Flame
 * @date 2020-03-12 11:09
 */

@Component
class RedisTokenManager @Autowired constructor(private val stringRedisTemplate: StringRedisTemplate) : AuthTokenManager {

    companion object {
        private const val USER_ID_PREFIX = "userId_"
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun createToken(userId: Long): String {
        var token = ""
        val clearToken =
            Date().time.toString() + AuthConstant.TOKEN_SEPARATOR + userId + AuthConstant.TOKEN_SEPARATOR + RandomStringUtils.randomAlphanumeric(10)
        try {
            val tokenBytes = encrypt(clearToken.toByteArray(), AuthConstant.AUTH_AES_KEY, AuthConstant.AUTH_AES_IV)
            token = EncodeUtils.bytesToHexString(tokenBytes)
            // 存储Token并设置过期时间
            stringRedisTemplate.opsForValue()[USER_ID_PREFIX + userId, token, AuthConstant.TOKEN_EXPIRE.toLong()] = TimeUnit.DAYS
        } catch (e: Exception) {
            log.error("Create token encrypt error: ${e.localizedMessage}")
            e.printStackTrace()
        }
        return token
    }

    override fun checkToken(token: String): Boolean {
        if (token.isNotEmpty()) {
            val userId = TokenUtils.fetchUserId(token)
            if (userId != 0L) {
                val redisToken = stringRedisTemplate.opsForValue()[USER_ID_PREFIX + userId]
                if (token == redisToken) {
                    // 延长过期时间
                    stringRedisTemplate.expire(USER_ID_PREFIX + userId, AuthConstant.TOKEN_EXPIRE.toLong(), TimeUnit.DAYS)
                    return true
                }
            }
        }
        return false
    }

    override fun deleteToken(userId: Long) {
        stringRedisTemplate.delete(USER_ID_PREFIX + userId)
    }
}