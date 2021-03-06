group = "com.hayate.mail"
description = "邮件模块"

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}
tasks.withType<Jar> {
    enabled = true
}

dependencies {
    implementation("org.springframework:spring-context-support:${ext.get("springContextVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-mail:${ext.get("springMailVersion")}")
}