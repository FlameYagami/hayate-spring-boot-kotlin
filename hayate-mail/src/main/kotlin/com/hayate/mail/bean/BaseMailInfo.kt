package com.hayate.mail.bean

/**
 * Created by Flame on 2020/03/18.
 */

open class BaseMailInfo {
    var host: String? = null
    var port = 0
    var username: String? = null
    var password: String? = null
    var fromAddress: String? = null
    var toAddress: String? = null
    var auth = false
}