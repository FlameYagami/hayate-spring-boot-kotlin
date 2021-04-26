package com.hayate.sms

import com.hayate.sms.service.intf.ISmsService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * 短信发送功能测试类
 * Created by Flame on 2020/03/19.
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HayateSmsApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SmsTest {

    @Autowired
    lateinit var iSMSService: ISmsService

    @Test
    fun testSMS() {
        val result = iSMSService.send("Replace Mobile To Test", "TestMessage")
        println("test result => $result")
        // 使用在主线程中使用join 等待子线程执行完
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}