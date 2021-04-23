package com.hayate.app.model.dto

/**
 * @author Flame
 * @date 2020-05-13 15:39
 */

data class UserInfoResponse(
    val userId: Long = 0,
    val account: String?,
    val nickname: String?,
    val avatar: String?
)