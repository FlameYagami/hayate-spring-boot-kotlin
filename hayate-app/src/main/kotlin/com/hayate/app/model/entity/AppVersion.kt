package com.hayate.app.model.entity

import com.baomidou.mybatisplus.annotation.TableName
import com.hayate.app.model.dto.AppVersionResponse

/**
 * @author Flame
 * @date 2020-05-18 16:41
 */

@TableName("t_dab_app_version")
data class AppVersion(
    var id: Long = 0,
    var version: String?,
    var url: String?,
    var platform: String?,
    var updateLog: String?,
    var forceVersion: String?
) {

    fun convertToAppVersionResponse(): AppVersionResponse {
        return AppVersionResponse(version = version, updateLog = updateLog)
    }
}