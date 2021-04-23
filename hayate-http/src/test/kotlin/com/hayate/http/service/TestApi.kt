package com.hayate.http.service

import com.hayate.http.model.UpdateInfo
import jdk.nashorn.internal.codegen.CompilerConstants

/**
 * Created by Flame on 2020/03/25.
 */
object TestApi : BaseApi() {
    fun checkAppUpdate(versionName: String?): CompilerConstants.Call<UpdateInfo> {
        return TestHolder.apiService.checkUpdate("android", versionName)
    }

    object TestHolder {
        var apiService = apiService(TestApiService.Companion.baseUrl, TestApiService::class.java)
    }
}