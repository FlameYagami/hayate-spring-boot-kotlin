package com.hayate.app.controller

import com.hayate.app.constant.GeneralConstant
import com.hayate.app.enums.ResultStatus
import com.hayate.app.model.dto.ApiResult
import com.hayate.app.service.intf.IUserService
import com.hayate.common.utils.FileUtils.deleteFile
import com.hayate.common.utils.FileUtils.writeFile
import com.hayate.common.utils.TimeUtils.getCurrentDate
import io.swagger.annotations.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api")
@Api(value = "个人信息接口", tags = ["个人信息接口"])
class ProfileController @Autowired constructor(
    private val iUserService: IUserService
) {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Value("\${filePath.avatar}")
    private lateinit var avatarBasePath: String

    @Value("\${baseUrl.avatar}")
    private lateinit var avatarBaseUrl: String

    @ApiOperation(value = "获取用户详细信息", notes = "根据用户的ID来获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "10000")
    @GetMapping("v1/user")
    fun fetchUserInfo(userId: Long): ApiResult<Any> {
        val user = iUserService.getById(userId)
        if (user == null) {
            log.error("User info error: account userId($userId) is not found")
            return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
        }
        val infoResponse = user.convertToUserInfoResponse("")
        return ApiResult.ok(infoResponse)
    }

    @ApiOperation(value = "修改用户昵称", notes = "修改用户昵称")
    @ApiImplicitParams(
        ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "10000"),
        ApiImplicitParam(name = "nickname", value = "昵称", required = true, example = "Dear God")
    )
    @PatchMapping("v1/nickname")
    fun modifyNickname(userId: Long, nickname: String): ApiResult<Any> {
        return if (iUserService.changeNickname(userId, nickname)) ApiResult.ok()
        else ApiResult.error(ResultStatus.FAILED)
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @ApiImplicitParams(
        ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "10000"),
        ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, example = "123456"),
        ApiImplicitParam(name = "newPassword", value = "新密码", required = true, example = "654321")
    )
    @PatchMapping("v1/password")
    fun changePassword(userId: Long, oldPassword: String, newPassword: String): ApiResult<Any> {
        return ApiResult.ok()
    }

    @ApiOperation(value = "修改头像", notes = "修改用户头像")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true, example = "10000")
    @PostMapping("v1/avatar")
    fun uploadAvatar(@ApiParam(value = "头像图片文件", required = true) @RequestParam("imageFile") imageFile: MultipartFile, userId: Long): ApiResult<Any> {
        val user = iUserService.getById(userId)
        if (user == null) {
            log.error("Upload avatar error: account userId($userId) is not found")
            return ApiResult.error(ResultStatus.ACCOUNT_NOT_FOUND)
        }
        val oldAvatar = user.avatar
        try {
            val avatarFilename =
                GeneralConstant.AVATAR_PREFIX + userId + GeneralConstant.AVATAR_SEPARATOR + getCurrentDate("yyyyMMddHHmmss") + GeneralConstant.AVATAR_EXTENSION
            val fileResult = writeFile(imageFile.bytes, avatarBasePath + avatarFilename)
            val dbResult = iUserService.changeAvatar(userId, avatarFilename)
            if (fileResult && dbResult) {
                if (!user.avatar.isNullOrEmpty()) {
                    deleteFile(avatarBasePath + oldAvatar)
                }
                val avatarUrl = avatarBaseUrl + avatarFilename
                val result: MutableMap<String, String> = HashMap()
                result["avatar"] = avatarUrl
                return ApiResult.ok(result)
            }
        } catch (e: Exception) {
            log.error("Upload avatar error: save avatar file failed")
        }
        return ApiResult.error(ResultStatus.FAILED)
    }
}