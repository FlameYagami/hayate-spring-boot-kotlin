group = "com.hayate.common"
description = "通用模块"

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("io.springfox:springfox-swagger2:${ext.get("swaggerVersion")}")
    implementation("io.springfox:springfox-swagger-ui:${ext.get("swaggerVersion")}")

    api("com.alibaba:druid-spring-boot-starter:${ext.get("springDruidVersion")}")
    api("com.baomidou:mybatis-plus-boot-starter:${ext.get("mybatisVersion")}")

    api("com.google.code.gson:gson:${ext.get("gsonVersion")}")
}