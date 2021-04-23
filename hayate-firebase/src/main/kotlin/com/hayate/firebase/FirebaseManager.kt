package com.hayate.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.util.*

object FirebaseManager {

    private val log = LoggerFactory.getLogger(this::class.java)

    private lateinit var firebaseApp: FirebaseApp

    init {
        val classPathResource = ClassPathResource("service-account-key.json")
        try {
            val inputStream = classPathResource.inputStream
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .setDatabaseUrl("")
                .build()
            // 初始化firebaseApp
            firebaseApp = FirebaseApp.initializeApp(options)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 单设备推送
     *
     * @param tokens 注册token
     * @param title  推送题目
     * @param body   推送内容
     */
    fun push(tokens: List<String>, title: String?, body: String?) {
        //构建消息内容
        val notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build()
        val message = MulticastMessage
                .builder()
                .setNotification(notification)
                .addAllTokens(tokens)
                .build()
        try {
            //发送后，返回messageID
            val response = FirebaseMessaging.getInstance(firebaseApp).sendMulticast(message)
            if(response.successCount == tokens.size) {
                log.info("Firebase push success: {}", tokens)
                return
            }
            // 收集推送失败信息
            val responses = response.responses
            val failedTokens: MutableList<String> = ArrayList()
            for(i in responses.indices) {
                if(!responses[i].isSuccessful) {
                    failedTokens.add(tokens[i])
                }
            }
            if(failedTokens.isNotEmpty()) {
                log.error("Firebase push failed: {}", failedTokens)
            }
        } catch(e: Exception) {
            log.error("Firebase push error: {}", e.message)
        }
    }
}