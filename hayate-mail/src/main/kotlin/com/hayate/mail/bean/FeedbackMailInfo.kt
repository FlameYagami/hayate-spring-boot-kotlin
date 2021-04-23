package com.hayate.mail.bean

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * 反馈邮件实体类
 */
@Component
@ConfigurationProperties(prefix = "feedback-mail")
class FeedbackMailInfo : BaseMailInfo()