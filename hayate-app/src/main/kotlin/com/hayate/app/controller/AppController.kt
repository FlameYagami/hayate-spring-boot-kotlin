package com.hayate.app.controller

import com.hayate.app.enums.ResultStatus
import com.hayate.app.model.dto.ApiResult
import com.hayate.common.utils.FileUtils.writeFile
import com.hayate.common.utils.TimeUtils.getCurrentDate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

/**
 * @author Flame
 * @date 2020-05-14 11:20
 */
@RestController
@RequestMapping("/api")
@Api(value = "客户端通用接口", tags = ["客户端通用接口"])
class AppController {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${filePath.fileLogs.app}")
    private lateinit var appLogPath: String

    @ApiOperation(value = "手机端上报日志", notes = "iOS/Android端把日志文件上报到服务端")
    @PostMapping("v1/app-log")
    fun uploadAppLog(@ApiParam(value = "日志文件", required = true) @RequestParam("logFile") logFile: MultipartFile): ApiResult<Any> {
        try {
            val filename = appLogPath + getCurrentDate("yyyy-MM-dd") + File.separator + logFile.originalFilename
            if(writeFile(logFile.bytes, filename)) {
                return ApiResult.ok()
            }
        } catch(e: Exception) {
            log.error("Upload app log error: upload file failed")
        }
        return ApiResult.error(ResultStatus.FAILED)
    }
}