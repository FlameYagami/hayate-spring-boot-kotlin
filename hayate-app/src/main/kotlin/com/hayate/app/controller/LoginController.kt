package com.hayate.app.controller

import com.hayate.app.constant.UserConstant
import com.hayate.app.enums.ResultStatus
import com.hayate.app.model.dto.ApiResult
import com.hayate.app.model.dto.SignUpRequest
import com.hayate.app.model.entity.User
import com.hayate.app.model.exception.ResultException
import com.hayate.app.service.intf.IUserService
import com.hayate.app.service.intf.IVerificationCodeService
import com.hayate.auth.manager.intf.AuthTokenManager
import com.hayate.common.utils.BcryptUtils
import com.hayate.mail.service.intf.IMailService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
@Api(value = "登录注册接口", tags = ["登录注册接口"])
class LoginController @Autowired constructor(
    private val authTokenManager: AuthTokenManager,
    private val iUserService: IUserService,
    private val iVerificationCodeService: IVerificationCodeService,
    private val iMailService: IMailService,
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ApiOperation(value = "注册账号", notes = "注册账号")
    @ApiImplicitParam(name = "request", value = "注册请求类", dataType = "SignUpRequest", required = true)
    @PostMapping("v1/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): ApiResult<Any> {
        // 检测用户是否存在
        if (iUserService.checkAccountExist(request.account.toString())) {
            log.error("Sign up error: account(${request.account}) already exist")
            return ApiResult.error(ResultStatus.ACCOUNT_ALREADY_EXIST)
        }

        // 检测密码是否有效
        val passwordResult = iUserService.validatePassword(request.password.toString())
        if (!passwordResult.isValid) {
            log.error("Sign up error: invalid password(${passwordResult.password.toString()})")
            return ApiResult.error(ResultStatus.INVALID_PASSWORD)
        }

        // 检测验证码的有效性
        if (validateVerificationCode(request.account.toString(), request.verificationCode.toString())) {
            // 密码加盐
            val hashPassword = BcryptUtils.encode(passwordResult.password.toString())
            val user = User(account = request.account.toString(), password = hashPassword)
            if (iUserService.save(user)) {
                return ApiResult.ok()
            }
        }
        log.error("Sign up error: account(${request.account}) sign up failed")
        return ApiResult.error(ResultStatus.FAILED)
    }

    @ApiOperation(value = "登录账号", notes = "登录账号")
    @ApiImplicitParams(
        ApiImplicitParam(name = "account", value = "账号", required = true, example = "17988187877"),
        ApiImplicitParam(name = "password", value = "密码", required = true, example = "123456")
    )
    @PatchMapping("v1/sign-in")
    fun signIn(account: String, password: String): ApiResult<Any> {
        return iUserService.findByAccount(account)?.takeIf {
            password == it.password
        }?.let {
            val token = authTokenManager.createToken(it.id!!)
            val response = it.convertToUserInfoResponse(token)
            ApiResult.ok(response)
        } ?: let {
            log.error("Sign in error: account($account) or password error")
            ApiResult.error(ResultStatus.ACCOUNT_OR_PASSWORD_ERROR)
        }
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @ApiImplicitParams(
        ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "10000"),
        ApiImplicitParam(name = "uuid", value = "手机端唯一识别号", required = true, example = "i:D833EB51-5FC4-4314-A862-FB511950F88D")
    )
    @PatchMapping("v1/sign-out")
    fun signOut(userId: Long, uuid: String?): ApiResult<Any> {
        val user = iUserService.getById(userId)
        if (user == null) {
            log.error("Sign out error: account userId($userId) is not found")
            return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
        }
        authTokenManager.deleteToken(userId)
        return ApiResult.ok()
    }

    @ApiOperation(value = "注销用户", notes = "注销用户")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "9999")
    @DeleteMapping("v1/user")
    fun disableUser(userId: Long): ApiResult<Any> {
        return if (iUserService.disableUser(userId)) {
            ApiResult.ok()
        } else ApiResult.error(ResultStatus.FAILED)
    }

    @ApiOperation(value = "发送验证码", notes = "发送注册验证码或忘记密码验证码")
    @ApiImplicitParams(
        ApiImplicitParam(name = "account", value = "账号", required = true, example = "17988187877"),
        ApiImplicitParam(name = "sendType", value = "发送类型 注册:SignUp 忘记密码:ForgetPwd", required = true, example = "ForgetPwd")
    )
    @PostMapping("v1/send-verify-code")
    fun sendVerificationCode(account: String, sendType: String): ApiResult<Any> {
        // 判断是注册还是忘记密码
        if (UserConstant.SIGN_UP_TYPE.equals(sendType, ignoreCase = true)) {
            if (iUserService.checkAccountExist(account)) {
                log.error("Send verification code error: account($account) already exist")
                return ApiResult.error(ResultStatus.ACCOUNT_ALREADY_EXIST)
            }
        } else if (UserConstant.FORGET_PASSWORD_TYPE.equals(sendType, ignoreCase = true)) {
            if (!iUserService.checkAccountExist(account)) {
                log.error("Send verification code error: account($account) is not exist")
                return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
            }
        } else {
            log.error("Send verification code error: type($sendType) error")
            return ApiResult.error(ResultStatus.VERIFY_CODE_WRONG_TYPE)
        }

        // 发送验证码
        var sendResult = false
        val verificationCode = "999999"

        // 存储验证码
        val dbResult: Boolean = iVerificationCodeService.saveVerificationCode(account, verificationCode)
        if (sendResult && dbResult) {
            return ApiResult.ok()
        }
        log.error("Send verification code error: send verification code to account($account) failed")
        return ApiResult.error(ResultStatus.SEND_VERIFY_CODE_FAIL)
    }

    @ApiOperation(value = "验证忘记密码的验证码", notes = "验证忘记密码的验证码是否正确")
    @ApiImplicitParams(
        ApiImplicitParam(name = "account", value = "账号", required = true, example = "17988187877"),
        ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, example = "635241")
    )
    @GetMapping("v1/validate-forget-password-code")
    fun validateForgetPasswordCode(account: String, verificationCode: String): ApiResult<Any> {
        // 检测用户是否存在
        if (!iUserService.checkAccountExist(account)) {
            log.error("Validate forget password verification code error: account($account) is not found")
            return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
        }

        // 检测验证码的有效性
        return if (validateVerificationCode(account, verificationCode)) {
            ApiResult.ok()
        } else ApiResult.error(ResultStatus.FAILED)
    }

    @ApiOperation(value = "忘记密码的修改密码", notes = "通过忘记密码的流程来修改密码")
    @ApiImplicitParams(
        ApiImplicitParam(name = "account", value = "账号", required = true, example = "17988187877"),
        ApiImplicitParam(name = "newPassword", value = "新密码", required = true, example = "123456"),
        ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, example = "635241")
    )
    @PatchMapping("v1/forget-password")
    fun forgetPassword(account: String, newPassword: String, verificationCode: String): ApiResult<Any> {
        // 检测用户是否存在
        if (!iUserService.checkAccountExist(account)) {
            log.error("Forget Password error: account($account) is not found")
            return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
        }

        // 检测验证码的有效性
        if (validateVerificationCode(account, verificationCode)) {
            // 密码加盐
            val hashPassword = ""
            if (iUserService.changePassword(account, hashPassword)) {
                return ApiResult.ok()
            }
        }
        log.error("Forget Password error: account($account) change password failed")
        return ApiResult.error(ResultStatus.FAILED)
    }

    // 检测验证码的有效性
    private fun validateVerificationCode(account: String, verificationCode: String): Boolean {
        // 检测验证码是否正确
        val verifyCode = iVerificationCodeService.findByAccount(account)
        if (verifyCode == null || verifyCode.code != verificationCode) {
            log.error("Validate verification code error: account($account) verification code($verificationCode) is not match")
            throw ResultException(ResultStatus.VERIFY_CODE_NOT_MATCH)
        }

        // 检测验证码是否超时
        val currentTime = Date().time
        val expireTime = verifyCode.updateDate?.time ?: 0 + UserConstant.VERIFICATION_CODE_EXPIRE_DURATION
        if (currentTime - expireTime > 0) {
            log.error("Validate verification code error: account($account) verification code($verificationCode) was expired")
            throw ResultException(ResultStatus.VERIFY_CODE_EXPIRED)
        }
        return true
    }

    // 发送邮件验证码
    private fun sendEmailVerificationCode(emailAccount: String, verificationCode: String): Boolean {
        val title = ""
        val content = "" + verificationCode
        return iMailService.sendVerificationCodeMail(emailAccount, title, content)
    }

    // 发送短信验证码
    private fun sendSMSVerificationCode(phoneAccount: String, verificationCode: String): Boolean {
        log.info("Send SMS phone number: {}", phoneAccount)
        return true
    }
}