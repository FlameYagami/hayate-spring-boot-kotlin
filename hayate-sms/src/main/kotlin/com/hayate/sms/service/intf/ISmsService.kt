package com.hayate.sms.service.intf

interface ISmsService {
    fun send(mobile: String, message: String): Boolean
}