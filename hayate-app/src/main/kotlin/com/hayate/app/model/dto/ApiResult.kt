package com.hayate.app.model.dto

import com.hayate.app.enums.ResultStatus

/**
 * @author Flame
 * @date 2020-03-23 15:07
 */

data class ApiResult<T : Any>(
    var resultCode: Int,
    var resultDesc: String?,
    var data: T?
) {

    private constructor(status: ResultStatus, data: T) : this(status.code, status.message, data)

    private constructor(status: ResultStatus) : this(status.code, status.message)

    private constructor(resultCode: Int, resultDesc: String?) : this(resultCode, resultDesc, null)

    companion object {
        fun ok(): ApiResult<Any> {
            return ApiResult(ResultStatus.SUCCESS)
        }

        fun <T : Any> ok(data: T): ApiResult<T> {
            return ApiResult(ResultStatus.SUCCESS, data)
        }

        fun error(status: ResultStatus): ApiResult<Any> {
            return ApiResult(status)
        }

        fun error(resultCode: Int, resultDesc: String?): ApiResult<Any> {
            return ApiResult(resultCode, resultDesc)
        }
    }
}