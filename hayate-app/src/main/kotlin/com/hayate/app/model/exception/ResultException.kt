package com.hayate.app.model.exception

import com.hayate.app.enums.ResultStatus

/**
 * @author Flame
 * @date 2020-05-08 10:37
 */

class ResultException(val resultStatus: ResultStatus) : RuntimeException()