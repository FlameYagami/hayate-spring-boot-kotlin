package com.hayate.app.model.bo

/**
 * @author Flame
 * @date 2020-05-12 11:41
 */

data class PasswordResult(
    val password: String?,
    val isValid: Boolean = false
)