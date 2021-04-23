package com.hayate.mail

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HayateMailApplication

fun main(args: Array<String>) {
    SpringApplication.run(HayateMailApplication::class.java, *args)
}