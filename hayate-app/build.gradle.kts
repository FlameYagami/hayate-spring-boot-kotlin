group = "com.hayate.app"
description = "应用模块"

dependencies {

    runtimeOnly("mysql:mysql-connector-java")

    implementation(project(":hayate-common"))
    implementation(project(":hayate-auth"))
    implementation(project(":hayate-mail"))
    implementation(project(":hayate-apns"))
    implementation(project(":hayate-firebase"))

    implementation("javax.validation:validation-api:${ext.get("validationVersion")}")

    implementation("io.springfox:springfox-swagger2:${ext.get("swaggerVersion")}")
    implementation("io.springfox:springfox-swagger-ui:${ext.get("swaggerVersion")}")

    implementation("org.springframework.boot:spring-boot-starter-aop")
}