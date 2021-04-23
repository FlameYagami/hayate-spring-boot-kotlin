group = "com.hayate.apns"
description = "iOS远程推送模块"

dependencies {
    implementation(project(":hayate-common"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("com.eatthepath:pushy:${ext.get("pushyVersion")}")
}