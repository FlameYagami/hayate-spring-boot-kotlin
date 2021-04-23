package com.hayate.app.constant

object UserConstant {
    const val SIGN_UP_TYPE = "SignUp"
    const val FORGET_PASSWORD_TYPE = "ForgetPwd"
    const val ACCOUNT_PHONE_TYPE = "phone"
    const val ACCOUNT_EMAIL_TYPE = "email"
    const val VERIFICATION_CODE_EXPIRE_DURATION = (10 * 60 * 1000).toLong()
}