package com.hayate.app.model.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.hayate.app.model.dto.UserInfoResponse

/**
 * @author Flame
 * @date 2020-04-01 15:32
 */

@TableName("t_ms_user")
data class User(
    val id: Long = 0,
    val account: String? = null,
    val nickname: String? = null,
    val password: String? = null,
    val avatar: String? = null,
    val enabled: String? = null
) {

    fun convertToInfoResponse(): UserInfoResponse {
        return UserInfoResponse(id, account, nickname, avatar)
    }
}