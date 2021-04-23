package com.hayate.app.interceptor

import com.hayate.app.enums.ResultStatus
import com.hayate.app.model.dto.ApiResult
import com.hayate.app.model.exception.ResultException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException

/**
 * @author Flame
 * @date 2020-03-24 10:50
 */

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResultException::class)
    fun handleResultException(resultException: ResultException): ApiResult<Any> {
        return ApiResult.error(resultException.resultStatus)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(ex: BindException): ApiResult<Any> {
        // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
        val fieldError = ex.fieldError
        val resultCode = ResultStatus.FAILED.code
        val resultDesc = fieldError?.defaultMessage + " => " + fieldError?.field + " : " + fieldError?.rejectedValue
        return ApiResult.error(resultCode, resultDesc)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationException(exception: ConstraintViolationException): ApiResult<Any> {
        val resultCode = ResultStatus.FAILED.code
        val resultDesc = exception.message
        return ApiResult.error(resultCode, resultDesc)
    }
}