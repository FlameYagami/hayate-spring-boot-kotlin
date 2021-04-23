package com.hayate.app

import org.springframework.test.context.junit4.SpringRunner

/**
 * @author Flame
 * @date 2021-01-05 10:08
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = HayateApplication::class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApnsTest {
    @Test
    fun apnsPushTest() {
        ApnsPushManager.INSTANCE.push(
                "8383366392c3001a11aea7b93a22bc0858e1e96d98f60f8ec2093c573528034b",
                "30119",
                "测测看",
                "My Test Good Job~",
                null,
                1)
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}