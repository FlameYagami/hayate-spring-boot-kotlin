group = "com.hayate.sms"
description = "Sms模块"

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation (project(":hayate-http"))
}