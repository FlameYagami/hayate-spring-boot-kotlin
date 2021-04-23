package com.hayate.app.model.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * @author Flame
 * @date 2020-05-09 16:50
 */

@ApiModel
data class SignUpRequest(
    @ApiModelProperty(value = "账号", required = true, example = "17988187877")
    val account: String?,
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    val password: String?,
    @ApiModelProperty(value = "注册验证码", required = true, example = "635241")
    val verificationCode: String?
)