package com.hayate.app.model.dto

/**
 * @author Flame
 * @date 2020-05-18 17:20
 */

data class AppVersionResponse(
    val version: String?,
    val updateLog: String?,
    var upgradeFlag: String? = null,
    var forceFlag: String? = null,
    var downloadUrl: String? = null
)