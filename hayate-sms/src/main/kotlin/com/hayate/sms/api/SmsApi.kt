package com.hayate.sms.api

import com.hayate.http.service.BaseApi
import com.hayate.sms.bean.SmsInfo
import com.hayate.sms.bean.SmsResponse
import retrofit2.Call

/**
 * Created by Flame on 2020/03/25.
 */

object SmsApi : BaseApi() {

    var apiService = service<ISms>(ISms.baseUrl)

    fun send(smsInfo: SmsInfo): Call<SmsResponse> {
        return apiService.sendSMS(smsInfo)
    }
}