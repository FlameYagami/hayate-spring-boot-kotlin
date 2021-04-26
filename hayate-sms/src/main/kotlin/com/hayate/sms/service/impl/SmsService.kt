package com.hayate.sms.service.impl

import com.hayate.sms.api.SmsApi
import com.hayate.sms.bean.SmsInfo
import com.hayate.sms.bean.SmsResponse
import com.hayate.sms.service.intf.ISmsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SmsService @Autowired constructor(private val smsInfo: SmsInfo) : ISmsService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun send(mobile: String, message: String): Boolean {
        smsInfo.mobile = mobile
        smsInfo.content = message
        return try {
            val response = SmsApi.send(smsInfo).execute()
            val smsResponse: SmsResponse = response.body() ?: throw Exception("Sms response is null")
            if (1 != smsResponse.resultCode) {
                log.error("Send Sms failed: mobile($mobile)")
                return false
            }
            log.info("Send Sms success: mobile($mobile)")
            true
        } catch (e: Exception) {
            log.error("Send Sms error: ${e.message}")
            false
        }
    }
}