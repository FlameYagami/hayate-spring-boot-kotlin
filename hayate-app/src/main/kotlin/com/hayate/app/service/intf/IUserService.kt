package com.hayate.app.service.intf

import com.baomidou.mybatisplus.extension.service.IService
import com.hayate.app.model.bo.PasswordResult
import com.hayate.app.model.entity.User

interface IUserService : IService<User> {
    // 检测账号是否存在
    fun checkAccountExist(account: String): Boolean

    // 通过账户名查询用户
    fun findByAccount(account: String): User?

    // 修改密码
    fun changePassword(account: String, password: String): Boolean

    // 校验密码合法性
    fun validatePassword(encryptPassword: String): PasswordResult

    // 解密密码
    fun decryptPassword(encryptPassword: String): String

    // 修改昵称
    fun changeNickname(userId: Long, nickname: String): Boolean

    // 修改头像
    fun changeAvatar(userId: Long, avatarPath: String): Boolean

    // 屏蔽用户
    fun disableUser(userId: Long): Boolean
}