group = "com.hayate.firebase"
description = "Firebase推送模块"

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation("com.google.firebase:firebase-admin:${ext.get("firebaseVersion")}")
}