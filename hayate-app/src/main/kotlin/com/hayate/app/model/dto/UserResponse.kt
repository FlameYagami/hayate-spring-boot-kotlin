package com.hayate.app.model.dto

/**
 * @author Flame
 * @date 2020-05-12 10:59
 */

data class UserResponse(
    val user: UserInfoResponse?,
    val token: String?
)