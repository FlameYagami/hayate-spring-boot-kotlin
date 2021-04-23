package com.hayate.app.enums

/**
 * @author Flame
 * @date 2020-03-23 15:06
 */

enum class ResultStatus(val code: Int, val message: String) {
    SUCCESS(1, "success"),
    FAILED(0, "server error"),
    ACCOUNT_OR_PASSWORD_ERROR(10001, "account or password error"),
    ACCOUNT_NOT_FOUND(10002, "account is not found"),
    USER_NOT_LOGIN(10003, "user not login"),
    INVALID_ACCOUNT(10004, "account is invalid"),
    ACCOUNT_ALREADY_EXIST(10005, "account is already exist"),
    VERIFY_CODE_WRONG_TYPE(10006, "parameter 'type' is wrong"),
    VERIFY_CODE_NOT_MATCH(10007, "verification code is not match"),
    VERIFY_CODE_EXPIRED(10008, "verification code was expired"),
    INVALID_PASSWORD(10009, "password is invalid"),
    SEND_VERIFY_CODE_FAIL(10010, "send verification code failed"),
    ACCOUNT_PASSWORD_ERROR(10011, "password is wrong"),
    CREATE_TOKEN_FAIL(10019, "create token failed"),
    INVALID_PARAM(20000, "invalid_param"),
}