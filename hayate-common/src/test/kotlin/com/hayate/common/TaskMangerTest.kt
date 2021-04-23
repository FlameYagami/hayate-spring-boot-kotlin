package com.hayate.common

import com.hayate.common.utils.TaskManager
import org.junit.Test
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Flame on 2021/01/25.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = HayateCommonApplication::class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskMangerTest {
    @Test
    fun test() {
        TaskManager.INSTANCE.submit(Thread { testThead() })
        try {
            Thread.sleep(5000)
        } catch (ignore: Exception) {
        }
        TaskManager.INSTANCE.shutdown()
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun testThead() {
        var count = 0
        try {
            Thread.sleep(7000)
            println("count: " + ++count)
        } catch (ignored: Exception) {
        }
    }
}