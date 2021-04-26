package com.hayate.apns.manager

import com.eatthepath.pushy.apns.ApnsClient
import com.eatthepath.pushy.apns.ApnsClientBuilder
import com.eatthepath.pushy.apns.PushNotificationResponse
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification
import com.eatthepath.pushy.apns.util.TokenUtil
import com.hayate.apns.constant.PushConstant
import com.hayate.apns.model.ApnsConfig
import com.hayate.common.utils.SpringBeanUtils
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.time.Instant

/**
 * @author Flame
 * @date 2020-07-07 16:53
 */


object ApnsPushManager {

    private val log = LoggerFactory.getLogger(this::class.java)

    lateinit var apnsClient: ApnsClient

    init {
        val eventLoopGroup: EventLoopGroup = NioEventLoopGroup(4)
        try {
            val apnsConfig = SpringBeanUtils.getBean(ApnsConfig::class.java)
            val apnsServer = if (apnsConfig.production) ApnsClientBuilder.PRODUCTION_APNS_HOST else ApnsClientBuilder.DEVELOPMENT_APNS_HOST
            val classPathResource = ClassPathResource(apnsConfig.credentialFilename)
            apnsClient = ApnsClientBuilder()
                    .setApnsServer(apnsServer)
                    .setClientCredentials(
                            classPathResource.inputStream,
                            PushConstant.P12_CREDENTIAL_PASSWORD
                    )
                    .setConcurrentConnections(4)
                    .setEventLoopGroup(eventLoopGroup)
                    .build()
        } catch (e: Exception) {
            log.error("APNS client create error: credentials file set failed: ${e.message}")
        }
    }

    fun push(deviceToken: String?, category: String?, alertTitle: String?, alertBody: String?, params: Map<String?, Any?>? = null, badge: Int = 1) {

        val payloadBuilder: ApnsPayloadBuilder = SimpleApnsPayloadBuilder()
        if (!alertTitle.isNullOrEmpty()) {
            payloadBuilder.setAlertTitle(alertTitle)
        }
        if (!alertBody.isNullOrEmpty()) {
            payloadBuilder.setAlertBody(alertBody)
        }
        if (!category.isNullOrEmpty()) {
            payloadBuilder.setCategoryName(category)
        }
        if (badge > 0) {
            payloadBuilder.setBadgeNumber(badge)
        }
        if (params != null && params.isNotEmpty()) {
            for ((key, value) in params) {
                payloadBuilder.addCustomProperty(key, value)
            }
        }
        //        payloadBuilder.setContentAvailable(true);
        payloadBuilder.setSound(PushConstant.APNS_SOUND)
        val payload = payloadBuilder.build()
        val token = TokenUtil.sanitizeTokenString(deviceToken)
        val pushNotification = SimpleApnsPushNotification(token, PushConstant.iOS_BUNDLE_ID, payload)
        apnsClient.sendNotification(pushNotification)
                .whenCompleteAsync { response: PushNotificationResponse<SimpleApnsPushNotification?>?, cause: Throwable ->
                    if (response == null) {
                        log.error("iOS push notification error: push notification response is null: ${cause.message} ")
                        return@whenCompleteAsync
                    }
                    if (response.isAccepted) {
                        log.info("iOS({}) push notification accepted by APNS gateway.", token)
                    } else {
                        log.error("iOS push notification error: device token($token) push notification rejected by the APNS gateway: ${response.rejectionReason}")
                        response.tokenInvalidationTimestamp.ifPresent { timestamp: Instant ->
                            log.error("\t and the token is invalid as of $timestamp")
                        }
                    }
                }
    }

    fun multiPush(
            deviceTokens: List<String?>,
            category: String?,
            alertTitle: String?,
            alertBody: String?,
            params: Map<String?, Any?>? = null,
            badge: Int = 1
    ) {
        for (deviceToken in deviceTokens) {
            push(deviceToken, category, alertTitle, alertBody, params, badge)
        }
    }
}