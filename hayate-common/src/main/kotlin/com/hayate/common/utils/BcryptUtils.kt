package com.hayate.common.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object BcryptUtils {

    fun encode(rawPassword: String): String {
        return BCryptPasswordEncoder().encode(rawPassword)
    }

    fun match(rawPassword: String, encodedPassword: String): Boolean {
        return BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
    }
}