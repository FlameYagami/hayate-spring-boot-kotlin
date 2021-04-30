package com.hayate.app

import com.hayate.common.HayateCommonApplication
import com.hayate.common.utils.TaskManager
import com.rabbitmq.client.Channel
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageDeliveryMode
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.concurrent.thread

/**
 * Created by Flame on 2021/01/25.
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HayateCommonApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RabbitmqTest {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @RabbitListener(queues = [RabbitmqConfig.QUEUE_A])
    fun receiveQueueA(msg: String, message: Message, channel: Channel) {
        println("Receive Queue A: $msg")
    }

    @RabbitListener(queues = [RabbitmqConfig.QUEUE_B])
    fun receiveQueueB(msg: String, message: Message, channel: Channel) {
        println("Receive Queue B: $msg")
    }

    @RabbitListener(queues = [RabbitmqConfig.QUEUE_C])
    fun receiveQueueC(msg: String, message: Message, channel: Channel) {
        println("Receive Queue C: $msg")
    }

    @Test
    fun testDirect() {
        TaskManager.submit(thread(false) { sendDirect("QueueA", RabbitmqConfig.QUEUE_A) })
        TaskManager.submit(thread(false) { sendDirect("QueueB", RabbitmqConfig.QUEUE_B) })
        TaskManager.submit(thread(false) { sendDirect("QueueC", RabbitmqConfig.QUEUE_C) })
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testFanout() {
        TaskManager.submit(thread(false) { sendFanout(RabbitmqConfig.FANOUT_EXCHANGE_NAME, "Fanout") })
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testTopic() {
        TaskManager.submit(thread(false) { sendTopic(RabbitmqConfig.TOPIC_EXCHANGE_NAME, "Topic", "topic1.test") })
        TaskManager.submit(thread(false) { sendTopic(RabbitmqConfig.TOPIC_EXCHANGE_NAME, "Topic", "topic2.test") })
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun testHeader() {
        TaskManager.submit(thread(false) {
            sendHeader(RabbitmqConfig.HEADER_EXCHANGE_NAME, "HeaderA", hashMapOf("header_a1" to "header_a1", "header_a2" to "header_a2"))
        })
        TaskManager.submit(thread(false) {
            sendHeader(RabbitmqConfig.HEADER_EXCHANGE_NAME, "HeaderB", hashMapOf("header_b2" to "header_b2"))
        })
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun sendDirect(queueName: String, routingKey: String) {
        var i = 0
        while (i++ != 3) {
            val message = "$queueName $i"
            rabbitTemplate.convertAndSend(routingKey, message)
            println("Send: $message")
        }
    }

    fun sendFanout(fanoutName: String, queueName: String) {
        var i = 0
        while (i++ != 3) {
            val message = "$queueName $i"
            rabbitTemplate.convertAndSend(fanoutName, "", message)
            println("Send: $message")
        }
    }

    fun sendTopic(topicName: String, queueName: String, routingKey: String) {
        var i = 0
        while (i++ != 3) {
            val message = "$queueName $i"
            rabbitTemplate.convertAndSend(topicName, routingKey, message)
            println("Send: $message")
        }
    }

    fun sendHeader(topicName: String, queueName: String, headerMap: Map<String, Any>) {
        val messageProperties = MessageProperties().apply {
            deliveryMode = MessageDeliveryMode.PERSISTENT
            contentType = "UTF-8"
            headers.putAll(headerMap);
        }
        val message = Message(queueName.toByteArray(), messageProperties)
        rabbitTemplate.convertAndSend(topicName, "", message)
        println("Send: $headerMap")
    }
}