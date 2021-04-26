import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
}

group = "com.hayate"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

allprojects {

    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        maven {
            url = uri("http://maven.aliyun.com/nexus/content/groups/public")
            url = uri("https://jitpack.io")
            url = uri("https://maven.google.com")
            url = uri("https://repo.spring.io/snapshot")
            url = uri("https://repo.spring.io/milestone")
        }
        mavenCentral()
        jcenter()
        flatDir { dirs("libs") }
    }
}

subprojects {

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")

    configurations.all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }

    dependencies {
        ext {
            set("swaggerVersion", "2.9.2")

            set("validationVersion", "2.0.1.Final")

            set("gsonVersion", "2.8.6")

            set("springDruidVersion", "1.1.23")
            set("mybatisVersion", "3.2.0")

            set("okhttpVersion", "3.14.9")
            set("retrofitVersion", "2.9.0")

            set("springContextVersion", "5.3.6")
            set("springMailVersion", "2.4.5")

            set("pushyVersion", "0.14.1")
            set("firebaseVersion", "6.14.0")

            set("commonsIOVersion", "2.7")
            set("commonsCodecVersion", "1.15")
        }

        runtimeOnly("org.springframework.boot:spring-boot-starter-tomcat")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-log4j2")

        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("org.apache.commons:commons-pool2")
        implementation("org.apache.commons:commons-lang3")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("junit:junit:4.12")

        implementation("com.google.protobuf:protobuf-java:3.14.0")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "1.8"
            }
        }
        withType<Test> {
            useJUnitPlatform()
        }
        withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
            archiveFileName.set("hayate.jar")
        }
    }
}


