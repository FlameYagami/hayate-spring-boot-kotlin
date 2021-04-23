package com.hayate.app.model.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Created by Flame on 2020/04/27.
 */

@ApiModel
data class DateRequest(
    @ApiModelProperty(value = "起始时间", required = true, example = "2016-01-01")
    val startTime: String?,
    @ApiModelProperty(value = "结束时间", required = true, example = "2021-01-01")
    val endTime: String?
)