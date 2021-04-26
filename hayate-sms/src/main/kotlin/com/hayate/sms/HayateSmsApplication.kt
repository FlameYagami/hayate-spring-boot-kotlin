package com.hayate.sms

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HayateSmsApplication

fun main(args: Array<String>) {
    SpringApplication.run(HayateSmsApplication::class.java, *args)
}