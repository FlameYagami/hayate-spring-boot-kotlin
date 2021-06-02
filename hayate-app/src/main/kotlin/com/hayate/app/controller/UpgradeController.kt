package com.hayate.app.controller

import com.hayate.app.constant.GeneralConstant
import com.hayate.app.enums.ResultStatus
import com.hayate.app.model.dto.ApiResult
import com.hayate.app.service.intf.IAppVersionService
import com.hayate.app.utils.CommonUtils
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Flame on 2020/08/17.
 */

@RestController
@RequestMapping("/api")
@Api(value = "升级管理接口", tags = ["升级管理接口"])
class UpgradeController @Autowired constructor(
    private val iAppVersionService: IAppVersionService
) {

    @Value("\${baseUrl.app}")
    private lateinit var appBaseUrl: String

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @ApiOperation(value = "检测APP版本升级", notes = "iOS/Android端APP检测是否有新版本需要升级")
    @ApiImplicitParams(
        ApiImplicitParam(name = "version", value = "版本号", required = true, example = "1.0"),
        ApiImplicitParam(name = "platform", value = "平台", required = true, example = "ios")
    )
    @GetMapping("v1/app-version")
    fun checkAppUpgrade(version: String, platform: String): ApiResult<Any> {
        val appVersion = iAppVersionService.findVersionByPlatform(platform)
        if (appVersion == null) {
            log.error("Check app upgrade error: version is not found by platform($platform)")
            return ApiResult.error(ResultStatus.FAILED)
        }
        val appVersionResponse = appVersion.convertToAppVersionResponse().apply {
            upgradeFlag = if (CommonUtils.versionCompare(version, appVersion.version ?: "")) GeneralConstant.NEED_UPGRADE
            else GeneralConstant.WITHOUT_UPGRADE
            forceFlag = if (CommonUtils.versionCompare(version, appVersion.forceVersion ?: "")) GeneralConstant.NEED_UPGRADE
            else GeneralConstant.WITHOUT_UPGRADE
            downloadUrl = "$appBaseUrl${appVersion.url}"
        }
        return ApiResult.ok(appVersionResponse)
    }
}