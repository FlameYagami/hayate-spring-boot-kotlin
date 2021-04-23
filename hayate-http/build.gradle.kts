group = "com.hayate.http"
description = "Http模块"

dependencies {
    api("com.squareup.okhttp3:okhttp:${ext.get("okhttpVersion")}")
    api("com.squareup.retrofit2:converter-gson:${ext.get("retrofitVersion")}")
}