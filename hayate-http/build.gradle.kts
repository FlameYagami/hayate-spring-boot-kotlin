group = "com.hayate.http"
description = "Http模块"

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}

dependencies {
    api("com.squareup.okhttp3:okhttp:${ext.get("okhttpVersion")}")
    api("com.squareup.retrofit2:converter-gson:${ext.get("retrofitVersion")}")
}