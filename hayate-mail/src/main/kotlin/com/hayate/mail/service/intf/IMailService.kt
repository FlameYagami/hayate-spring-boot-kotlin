package com.hayate.mail.service.intf

import com.hayate.mail.bean.BaseMailInfo

interface IMailService {
    fun sendVerificationCodeMail(address: String, title: String, message: String): Boolean
    fun sendFeedbackMail(address: String, title: String, message: String): Boolean
    fun sendMail(baseMailInfo: BaseMailInfo, title: String, message: String): Boolean
}