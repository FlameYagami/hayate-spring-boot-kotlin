package com.hayate.app

import com.hayate.apns.manager.ApnsPushManager
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Flame
 * @date 2021-01-05 10:08
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HayateApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApnsTest {

    @Test
    fun apnsPushTest() {
        ApnsPushManager.push(
                "",
                "",
                "",
                "",
                null,
                1
        )
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}