package com.hayate.app.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hayate.app.constant.DbConstant
import com.hayate.app.constant.RegexConstant
import com.hayate.app.mapper.UserMapper
import com.hayate.app.model.bo.PasswordResult
import com.hayate.app.model.entity.User
import com.hayate.app.service.intf.IUserService
import com.hayate.common.constant.CommonConstants
import com.hayate.common.utils.AesUtils.decrypt
import com.hayate.common.utils.EncodeUtils
import org.springframework.stereotype.Service

/**
 * @author Flame
 * @date 2020-04-02 11:11
 */

@Service
class UserService : ServiceImpl<UserMapper, User>(), IUserService {

    // 检测账号是否存在
    override fun checkAccountExist(account: String): Boolean {
        return null == findByAccount(account)
    }

    // 通过账户名查询用户
    override fun findByAccount(account: String): User? {
        val qwUser = QueryWrapper<User>()
            .eq(DbConstant.ACCOUNT, account)
            .eq(DbConstant.ENABLED, DbConstant.ENABLE)
        return getOne(qwUser)
    }

    // 修改密码
    override fun changePassword(account: String, password: String): Boolean {
        val uwUser = UpdateWrapper<User>()
            .set(DbConstant.PASSWORD, password)
            .eq(DbConstant.ACCOUNT, account)
            .eq(DbConstant.ENABLED, DbConstant.ENABLE)
        return update(uwUser)
    }

    // 校验密码合法性
    override fun validatePassword(encryptPassword: String): PasswordResult {
        val password = decryptPassword(encryptPassword)
        return PasswordResult(password, password.isNotEmpty() && password.matches(Regex(RegexConstant.PASSWORD_REGEX)))
    }

    // 解密密码
    override fun decryptPassword(encryptPassword: String): String {
        val clearPwdBytes = decrypt(EncodeUtils.hexStringToBytes(encryptPassword), CommonConstants.DEFAULT_AES_KEY, CommonConstants.DEFAULT_AES_IV)
        val clearPassword = String(clearPwdBytes)
        var password = ""
        if (clearPassword.isNotEmpty()) {
            val clearPasswordArray = clearPassword.split(RegexConstant.PASSWORD_SEPARATOR_REGEX).toTypedArray()
            if (clearPasswordArray.size > 1) {
                password = clearPasswordArray[1].trim { it <= ' ' }
            }
        }
        return password
    }

    // 修改昵称
    override fun changeNickname(userId: Long, nickname: String): Boolean {
        val uwUser = UpdateWrapper<User>()
            .set(DbConstant.NICKNAME, nickname)
            .eq(DbConstant.ID, userId)
            .eq(DbConstant.ENABLED, DbConstant.ENABLE)
        return update(uwUser)
    }

    // 修改头像
    override fun changeAvatar(userId: Long, avatarPath: String): Boolean {
        val uwUser = UpdateWrapper<User>()
            .set(DbConstant.AVATAR, avatarPath)
            .eq(DbConstant.ID, userId)
            .eq(DbConstant.ENABLED, DbConstant.ENABLE)
        return update(uwUser)
    }

    // 屏蔽用户
    override fun disableUser(userId: Long): Boolean {
        val uwUser = UpdateWrapper<User>()
            .set(DbConstant.ENABLED, DbConstant.DISABLE)
            .eq(DbConstant.ID, userId)
        return update(uwUser)
    }
}