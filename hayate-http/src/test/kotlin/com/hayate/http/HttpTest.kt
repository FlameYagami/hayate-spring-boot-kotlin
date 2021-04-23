package com.hayate.http

import com.google.gson.Gson
import com.hayate.http.func.CallbackFunc
import com.hayate.http.model.UpdateInfo
import com.hayate.http.service.TestApi
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by Flame on 2020/03/25.
 */

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [HayateHttpApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpTest {

    @Test
    fun testHttp() {
        TestApi.checkAppUpdate("1.5.0").enqueue(object : CallbackFunc<UpdateInfo>() {
            override fun onResponse(response: UpdateInfo?) {
                println(Gson().toJson(response))
            }

            override fun onFailure(throwable: Throwable) {
                println(throwable.message)
            }
        })
        try {
            Thread.currentThread().join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}