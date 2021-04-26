package com.hayate.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.slf4j.LoggerFactory

/**
 * Http拦截器
 * Created by Flame on 2020/03/24.
 */

class LogInterceptor : Interceptor {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        log.info("Http request => $request")
        val response = chain.proceed(chain.request())
        val mediaType = response.body()?.contentType()
        val content = response.body()?.string().toString()
        log.info("Http response body => $content")
        return response.newBuilder()
            .body(ResponseBody.create(mediaType, content))
            .build()
    }
}