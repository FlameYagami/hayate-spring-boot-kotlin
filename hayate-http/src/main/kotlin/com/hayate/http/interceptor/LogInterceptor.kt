package com.hayate.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory
import java.io.IOException

/**
 * Http拦截器
 * Created by Flame on 2020/03/24.
 */

class LogInterceptor : Interceptor {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        log.info("Http request => {}", request)
        val response = chain.proceed(chain.request())
        val responseBody = response.body()
        val content = responseBody!!.string()
        val mediaType = response.body()!!.contentType()
        log.info("Http response => {}", content)
        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }
}