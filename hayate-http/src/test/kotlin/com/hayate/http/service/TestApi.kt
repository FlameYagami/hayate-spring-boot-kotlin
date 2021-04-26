package com.hayate.http.service

import com.hayate.http.model.UpdateInfo
import retrofit2.Call

/**
 * Created by Flame on 2020/03/25.
 */
object TestApi : BaseApi() {

    var apiService = service<TestApiService>(TestApiService.baseUrl)

    fun checkAppUpdate(versionName: String): Call<UpdateInfo> {
        return apiService.checkUpdate("android", versionName)
    }
}