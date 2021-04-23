group = "com.hayate.mail"
description = "邮件模块"

dependencies {
    implementation("org.springframework:spring-context-support:${ext.get("springContextVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-mail:${ext.get("springMailVersion")}")
}