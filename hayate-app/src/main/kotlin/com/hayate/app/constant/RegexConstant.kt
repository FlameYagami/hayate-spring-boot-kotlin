package com.hayate.app.constant

/**
 * @author Flame
 * @date 2020-04-20 11:28
 */
object RegexConstant {
    const val PHONE_REGEX = "^0?(1[3-9][0-9])[0-9]{8}$"
    const val EMAIL_REGEX = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$"
    const val PASSWORD_REGEX = "^.{6,20}$"
    const val PASSWORD_SEPARATOR_REGEX = "\\|"
}