package com.hayate.app.model.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.hayate.app.model.dto.UserInfoResponse

@TableName("t_dab_user")
data class User(
    val id: Long? = null,
    val account: String,
    val nickname: String? = null,
    val password: String,
    val avatar: String? = null,
    val enabled: String? = null
) {

    fun convertToUserInfoResponse(token: String): UserInfoResponse {
        return UserInfoResponse(id?.toLong() ?: 0, account, nickname, avatar, token)
    }
}