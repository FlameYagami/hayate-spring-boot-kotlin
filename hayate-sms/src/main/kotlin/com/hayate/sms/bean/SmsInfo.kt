package com.hayate.sms.bean

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Created by Flame on 2020/03/18.
 */

@Component
@ConfigurationProperties(prefix = "sms")
class SmsInfo {

    // 以下为自定义属性,需要填充
    var mobile: String? = null
    var content: String? = null
}