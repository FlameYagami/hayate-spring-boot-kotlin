package com.hayate.http.service

import com.hayate.http.interceptor.LogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Flame on 2020/03/25.
 */

abstract class BaseApi {

    companion object {
        const val DEFAULT_TIMEOUT = 5000L // 默认超时时间5秒
    }

    inline val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(LogInterceptor())
                .build()
        }

    inline fun <reified T> service(baseUrl: String): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(T::class.java)
    }
}