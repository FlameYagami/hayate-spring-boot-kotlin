package com.hayate.app.model.dto

data class UserInfoResponse(
    val userId: Long = 0,
    val account: String,
    val nickname: String?,
    val avatar: String?,
    val token: String
)