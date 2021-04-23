package com.hayate.http.func

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Flame on 2020/03/25.
 */
abstract class CallbackFunc<T> : Callback<T> {
    abstract fun onResponse(response: T?)
    abstract fun onFailure(throwable: Throwable)
    override fun onResponse(call: Call<T>, response: Response<T>) {
        onResponse(response.body())
    }

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        onFailure(throwable)
    }
}