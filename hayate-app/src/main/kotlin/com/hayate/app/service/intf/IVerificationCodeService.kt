package com.hayate.app.service.intf

import com.baomidou.mybatisplus.extension.service.IService
import com.hayate.app.model.entity.VerificationCode

/**
 * @author Flame
 * @date 2020-04-17 11:50
 */

interface IVerificationCodeService : IService<VerificationCode> {
    fun findByAccount(account: String): VerificationCode?
    fun saveVerificationCode(account: String, verificationCode: String): Boolean
}