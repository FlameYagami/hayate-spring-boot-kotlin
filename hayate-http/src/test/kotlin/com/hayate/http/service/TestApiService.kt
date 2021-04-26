package com.hayate.http.service

import com.hayate.http.model.UpdateInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Flame on 2020/03/25.
 */

interface TestApiService {

    companion object {
        const val baseUrl = ""
    }

    @GET("")
    fun checkUpdate(
        @Query("") platform: String,
        @Query("") version: String
    ): Call<UpdateInfo>
}