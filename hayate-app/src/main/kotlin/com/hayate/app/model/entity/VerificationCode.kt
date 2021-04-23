package com.hayate.app.model.entity

import com.baomidou.mybatisplus.annotation.TableName
import java.util.*

/**
 * @author Flame
 * @date 2020-04-17 11:47
 */

@TableName("t_ms_verification_code")
data class VerificationCode(
    val id: Long = 0,
    val account: String? = null,
    val code: String? = null,
    val updateDate: Date? = null,
)