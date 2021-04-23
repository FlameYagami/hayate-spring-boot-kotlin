package com.hayate.app.model.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Created by Flame on 2020/04/20.
 */

@ApiModel
data class PageRequest(
    @ApiModelProperty(value = "分页大小", dataType = "Long", required = true, example = "15")
    val pageSize: Int = 0,
    @ApiModelProperty(value = "分页页数", dataType = "Long", required = true, example = "0")
    val pageCount: Int = 0
)