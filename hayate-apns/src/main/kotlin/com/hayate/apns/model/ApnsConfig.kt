package com.hayate.apns.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author Flame
 * @date 2020-12-30 10:25
 */
@Component
@ConfigurationProperties(prefix = "apns")
class ApnsConfig {
    var production = false
    var credentialFilename = ""
}