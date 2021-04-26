package com.hayate.mail.service.impl

import com.hayate.mail.bean.BaseMailInfo
import com.hayate.mail.bean.FeedbackMailInfo
import com.hayate.mail.bean.VerifyMailInfo
import com.hayate.mail.constant.MailConstant
import com.hayate.mail.service.intf.IMailService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class MailService @Autowired constructor(
    private val feedbackMailInfo: FeedbackMailInfo,
    private val verifyMailInfo: VerifyMailInfo
) : IMailService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * 发送注册邮件
     * @param address 邮件地址
     * @param title 邮件标题
     * @param message 邮件内容
     * @return 邮件发送结果
     */
    override fun sendVerificationCodeMail(
        address: String,
        title: String,
        message: String
    ): Boolean {
        verifyMailInfo.toAddress = address
        return sendMail(verifyMailInfo, title, message)
    }

    /**
     * 发送反馈邮件
     * @param address 邮件地址
     * @param title 邮件标题
     * @param message 邮件内容
     * @return 邮件发送结果
     */
    override fun sendFeedbackMail(address: String, title: String, message: String): Boolean {
        feedbackMailInfo.toAddress = address
        return sendMail(feedbackMailInfo, title, message)
    }

    private fun getProperties(baseMailInfo: BaseMailInfo): Properties {
        // 因为阿里云的25端口被禁用, 所以使用第三方的465端口进行发送
        val properties = Properties()
        properties[MailConstant.AUTH] = true.toString()
        properties[MailConstant.HOST] = baseMailInfo.host
        properties[MailConstant.PORT] = baseMailInfo.port
        properties[MailConstant.FACTORY_PORT] = baseMailInfo.port
        properties[MailConstant.FACTORY_CLASS] = MailConstant.FACTORY_CLASS_VALUE
        properties[MailConstant.FACTORY_FALLBACK] = false.toString()
        properties[MailConstant.USERNAME] = baseMailInfo.username
        properties[MailConstant.PASSWORD] = baseMailInfo.password
        return properties
    }

    /**
     * 发送邮件
     * @param baseMailInfo 邮件的基础配置
     * @param title 邮件标题
     * @param message 邮件内容
     * @return 邮件发送结果
     */
    override fun sendMail(baseMailInfo: BaseMailInfo, title: String, message: String): Boolean {
        val properties = getProperties(baseMailInfo)
        var authenticator: Authenticator? = null
        if (baseMailInfo.auth) {
            authenticator = object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(baseMailInfo.username, baseMailInfo.password)
                }
            }
        }
        // 使用环境属性和授权信息，创建邮件会话
        val mailSession = Session.getInstance(properties, authenticator)
        // 创建邮件消息
        val mimeMessage = MimeMessage(mailSession)
        return try {
            // 设置发件人
            val from = InternetAddress(baseMailInfo.fromAddress)
            mimeMessage.setFrom(from)
            val addresses = arrayOf<Address>(InternetAddress(baseMailInfo.fromAddress))
            mimeMessage.replyTo = addresses
            // 设置收件人
            val toAddress = InternetAddress(baseMailInfo.toAddress)
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, toAddress)
            // 设置邮件标题
            mimeMessage.subject = title
            // 设置邮件的内容体
            mimeMessage.setContent(message, "text/html;charset=UTF-8")
            // 发送邮件
            Transport.send(mimeMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("Send mail error: ${e.message}")
            false
        }
    }
}