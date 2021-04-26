group = "com.hayate.apns"
description = "iOS远程推送模块"

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation(project(":hayate-common"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("com.eatthepath:pushy:${ext.get("pushyVersion")}")
}