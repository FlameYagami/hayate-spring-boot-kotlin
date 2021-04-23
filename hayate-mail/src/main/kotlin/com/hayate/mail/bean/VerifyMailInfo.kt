package com.hayate.mail.bean

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 注册邮件实体类
 */
@Component
@ConfigurationProperties(prefix = "verify-mail")
class VerifyMailInfo : BaseMailInfo()