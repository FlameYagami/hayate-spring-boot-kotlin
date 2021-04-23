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

    private object OkHttpClientHolder {
        val instance = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(LogInterceptor())
            .build()
    }

    companion object {

        private const val DEFAULT_TIMEOUT = 5000L // 默认超时时间5秒

        fun <T> apiService(baseUrl: String, tClass: Class<T>): T {
            return Retrofit.Builder()
                    .client(OkHttpClientHolder.instance)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                    .create(tClass)
        }
    }
}