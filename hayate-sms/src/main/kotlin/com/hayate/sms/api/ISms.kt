package com.hayate.sms.api

import com.hayate.sms.bean.SmsInfo
import com.hayate.sms.bean.SmsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Flame on 2020/03/25.
 */

interface ISms {

    companion object {
        const val baseUrl = ""
    }

    @POST("")
    fun sendSMS(@Body smsInfo: SmsInfo): Call<SmsResponse>
}