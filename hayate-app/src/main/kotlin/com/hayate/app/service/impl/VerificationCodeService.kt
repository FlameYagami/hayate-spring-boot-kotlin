package com.hayate.app.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hayate.app.constant.DbConstant
import com.hayate.app.mapper.VerificationCodeMapper
import com.hayate.app.model.entity.VerificationCode
import com.hayate.app.service.intf.IVerificationCodeService
import org.springframework.stereotype.Service

/**
 * @author Flame
 * @date 2020-04-17 11:51
 */

@Service
class VerificationCodeService : ServiceImpl<VerificationCodeMapper, VerificationCode>(), IVerificationCodeService {

    override fun findByAccount(account: String): VerificationCode? {
        val qwVerificationCode = QueryWrapper<VerificationCode>()
            .eq(DbConstant.ACCOUNT, account)
        return getOne(qwVerificationCode)
    }

    override fun saveVerificationCode(account: String, verificationCode: String): Boolean {
        val qwVerificationCode = QueryWrapper<VerificationCode>()
            .eq(DbConstant.ACCOUNT, account)
        val verifyCode = VerificationCode(account = account, code = verificationCode)
        return saveOrUpdate(verifyCode, qwVerificationCode)
    }
}